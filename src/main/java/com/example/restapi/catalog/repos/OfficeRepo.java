package com.example.restapi.catalog.repos;

import com.example.restapi.catalog.controller.OfficeController;
import com.example.restapi.catalog.model.Office;
import com.example.restapi.catalog.model.Organization;
import com.example.restapi.catalog.rawModel.RawOrganization;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OfficeRepo extends JpaRepository<Office, Integer> {
    List<Office> findByOrgId(Organization orgClassId);
    Office findByOrgIdAndIsMain (Organization OrgId, Boolean IsMain);
    //Office findByOrgId(RawOrganization orgClassId);
    List<Office> findAllByOrgId (Organization orgId);

}
