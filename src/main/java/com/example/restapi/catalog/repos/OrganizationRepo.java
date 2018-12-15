package com.example.restapi.catalog.repos;

import com.example.restapi.catalog.model.Organization;
import com.example.restapi.catalog.rawModel.RawOrganization;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrganizationRepo extends JpaRepository<Organization, Integer> {

    Organization findOrgByOrgId(Integer orgId);
    List<Organization> findOrgByName(String name);
   /// List<RawOrganization> findOrgByName(String name);

}

