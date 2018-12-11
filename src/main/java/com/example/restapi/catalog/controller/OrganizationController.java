package com.example.restapi.catalog.controller;

import com.example.restapi.catalog.model.Organization;
import com.example.restapi.catalog.repos.OrganizationRepo;
import com.example.restapi.catalog.service.OrganizationService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("organization")
public class OrganizationController {

    private final OrganizationService organizationService;

    @Autowired
    public OrganizationController(OrganizationService organizationService) {this.organizationService = organizationService;}

    /// api/organization/list

    @GetMapping("/list")
    public List<Organization> orgList() {return  organizationService.getOrgList();
    }

    @GetMapping("{id}")
    public Organization org(@PathVariable("id") Organization organization){return organization; }

    @PostMapping("/save")
    public Organization save(@RequestBody Organization organization) { return organizationService.add(organization);}

    @PostMapping("update/{id}")
    public Organization update(
            @PathVariable("id") Organization organizationFromDB,
            @RequestBody Organization organizationFromWeb){
         return organizationService.update(organizationFromWeb, organizationFromDB);
    }


}
