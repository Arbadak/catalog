package com.example.restapi.catalog.controller;

import com.example.restapi.catalog.model.Office;
import com.example.restapi.catalog.model.Organization;
import com.example.restapi.catalog.rawModel.RawOrganization;
import com.example.restapi.catalog.repos.OrganizationRepo;
import com.example.restapi.catalog.service.OrganizationService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/***
 *  этот контроллер недоделан и будет дополнен
 *
 */
@RestController
@RequestMapping("organization")
public class OrganizationController {

    private final OrganizationService organizationService;

    @Autowired
    public OrganizationController(OrganizationService organizationService) {this.organizationService = organizationService;}

    /// api/organization/list

    @PostMapping("/list")
    public List<RawOrganization> orgList(@RequestBody RawOrganization rawOrganization) {return  organizationService.getOrgList(rawOrganization);
    }

    @GetMapping("{id}")
    public String org(@PathVariable("id") Integer id){return organizationService.getOne(id); }

    @PostMapping("/save")
    public String save(@RequestBody RawOrganization rawOrganization){

        return organizationService.add(rawOrganization);
    }

    @PostMapping("update/{id}")
    public String update(
            @PathVariable("id") Organization organizationFromDB,
            @RequestBody RawOrganization organizationFromWeb){
         return organizationService.update(organizationFromWeb, organizationFromDB);
    }


}
