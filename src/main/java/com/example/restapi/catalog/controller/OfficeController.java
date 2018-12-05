package com.example.restapi.catalog.controller;

import com.example.restapi.catalog.model.Office;
import com.example.restapi.catalog.repos.OfficeRepo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("office")
public class OfficeController {

   private final OfficeRepo officeRepo;

  @Autowired
    public OfficeController(OfficeRepo officeRepo) {
        this.officeRepo = officeRepo;
    }


    @GetMapping
    public List<Office> list() {
        return  officeRepo.findAll();
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
