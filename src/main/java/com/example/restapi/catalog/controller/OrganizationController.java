package com.example.restapi.catalog.controller;

import com.example.restapi.catalog.model.Organization;
import com.example.restapi.catalog.repos.OrganizationRepo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("organization")
public class OrganizationController {

    private final OrganizationRepo organizationRepo;

    @Autowired
    public OrganizationController(OrganizationRepo organizationRepo) {
        this.organizationRepo = organizationRepo;
    }


    @GetMapping
    public List<Organization> list() {
        return  organizationRepo.findAll();
    }

    @GetMapping("{id}")
    public Organization getOne(@PathVariable("id") Organization organization){
        return organization;
    }

    @PostMapping("save")
    public Organization create(@RequestBody Organization organization) {
        return organizationRepo.save(organization);
    }

    @PostMapping("update/{id}")
    public Organization update(
            @PathVariable("id") Organization organizationFromDB,
            @RequestBody Organization organizationFromWeb
    ){
        BeanUtils.copyProperties(organizationFromWeb, organizationFromDB, "id");
        return organizationRepo.save(organizationFromDB);
    }


}
