package com.example.restapi.catalog.service;


import com.example.restapi.catalog.model.Office;
import com.example.restapi.catalog.model.Organization;
import com.example.restapi.catalog.repos.OfficeRepo;
import com.example.restapi.catalog.repos.OrganizationRepo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrganizationService {

    private final OrganizationRepo organizationRepo;
    private final OfficeRepo officeRepo;

    @Autowired
    public OrganizationService(OrganizationRepo organizationRepo, OfficeRepo officeRepo) {
        this.organizationRepo = organizationRepo;
        this.officeRepo = officeRepo;
    }

    public List<Organization> getOrgList() {

        List<Organization> all = organizationRepo.findAll();
        return all;
    }

    public Organization add(Organization organization) {
        return organizationRepo.save(organization);
    }

    public Organization update(Organization orgSource, Organization orgDest) {
        BeanUtils.copyProperties(orgSource, orgDest, "id");
        return organizationRepo.save(orgDest);
    }

    public String getOne(Integer id) {

        Organization organization = organizationRepo.findOrgByOrgId(id);
        Office mainOffice = officeRepo.findByOrgIdAndIsMain(organization, true);
        //return organization.toString()+mainOffice.toString();
        String result= new StringBuilder()
                .append("{\n ").append("id:").append(organization.getOrgId())
                .append("\nname:").append(organization.getShortNameOfOranization())
                .append("\nfullName:").append(organization.getNameOfOranization())
                .append("\ninn:").append(organization.getInn())
                .append("\nkpp:").append(organization.getKpp())
                .append("\naddress:").append(mainOffice.getAddress())
                .append("\nphone:").append(mainOffice.getPhoneOffice())
                .append("\nisActive:").append(mainOffice.getActive())
                .append("\n}").toString();
        return result;


    }

}