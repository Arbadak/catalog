package com.example.restapi.catalog.controller;

import com.example.restapi.catalog.model.Office;
import com.example.restapi.catalog.repos.OfficeRepo;
import com.example.restapi.catalog.repos.OrganizationRepo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("office")
public class OfficeController {

   private final OfficeRepo officeRepo;

  @Autowired
    public OfficeController(OfficeRepo officeRepo) {
        this.officeRepo = officeRepo;
    }


    @GetMapping("/list/{id}")
    public Optional<Office> list(@PathVariable("orgID") Integer orgId) {

      return  officeRepo.findById(orgId);
    }

    @GetMapping("{id}")
        public Office getOne(@PathVariable("id") Office office){
     return office;
    }

    @PostMapping("save")
    public Office create(@RequestBody Office office) {
      return officeRepo.save(office);
    }

    @PostMapping("update/{id}")
    public Office update(
            @PathVariable("id") Office officeFromDB,
            @RequestBody Office officeFromWeb
    ){
        BeanUtils.copyProperties(officeFromWeb, officeFromDB, "id");
        return officeRepo.save(officeFromDB);
    }


}
