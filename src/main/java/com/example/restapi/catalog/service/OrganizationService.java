package com.example.restapi.catalog.service;


import com.example.restapi.catalog.model.Organization;
import com.example.restapi.catalog.repos.OrganizationRepo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrganizationService {

    private final OrganizationRepo organizationRepo;

    @Autowired
    public OrganizationService(OrganizationRepo organizationRepo) {this.organizationRepo = organizationRepo;}

    public List<Organization>getOrgList (){

    List<Organization>all=organizationRepo.findAll();
    return all;
    }

    public Organization add (Organization organization){
        return organizationRepo.save(organization);
    }

    public Organization update (Organization orgSource, Organization orgDest){
        BeanUtils.copyProperties(orgSource,orgDest,"id");
        return  organizationRepo.save(orgDest);}
}
