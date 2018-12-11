package com.example.restapi.catalog.repos;

import com.example.restapi.catalog.model.Organization;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrganizationRepo extends JpaRepository<Organization, Integer> {

    Organization findOrgByOrgId(Organization orgId);
}

