package com.example.restapi.catalog.controller;

import com.example.restapi.catalog.model.Office;
import com.example.restapi.catalog.rawModel.RawOffice;
import com.example.restapi.catalog.repos.OfficeRepo;
import com.example.restapi.catalog.repos.OrganizationRepo;
import com.example.restapi.catalog.service.OfficeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.restapi.catalog.rawModel.resultResponce;
import java.util.List;
import java.util.Optional;

/*** Этот контроллер неправильный и будет переделан
 *
 */

@RestController
@RequestMapping("office")
public class OfficeController {

   //private final OfficeRepo officeRepo;
    private final OfficeService officeService;

  @Autowired
    public OfficeController(OfficeService officeService) {
        this.officeService = officeService;
    }


    @GetMapping("/list/{id}")
    //public Optional<Office> list(@PathVariable("orgID") Integer orgId) {
    public List<Office> officeList (@PathVariable("orgID") Integer orgId){

      return officeService.getOfficeList(orgId);
    }

    @GetMapping("{id}")
         //public Office getOne(@PathVariable("id") Office office){  return office; }
    public Office getOne (@PathVariable("id") Integer officeId){

      return officeService.getOne (officeId);
    }

    @PostMapping("save")
    public resultResponce save(@RequestBody RawOffice office) {
      return officeService.add(office);
    }

    @PostMapping("update")
    public resultResponce update(@RequestBody RawOffice office) {
        return officeService.update(office);
    }

   /* @PostMapping("update/{id}")
    public Office update(
            @PathVariable("id") Office officeFromDB,
            @RequestBody Office officeFromWeb
    ){
        BeanUtils.copyProperties(officeFromWeb, officeFromDB, "id");
        return officeRepo.save(officeFromDB);
    }*/



}
