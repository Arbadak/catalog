package com.example.restapi.catalog.service;

import com.example.restapi.catalog.model.Organization;
import com.example.restapi.catalog.rawModel.RawOrganization;
import com.example.restapi.catalog.rawModel.ResultResponce;

import java.util.List;

/**
 * Интерфейс с описанием методов для запросов вида /api/organization
 */
public interface OrganizationService {

    List<RawOrganization> getOrgList(RawOrganization rawOrganization);

    ResultResponce add(RawOrganization rawOrganization);

    ResultResponce update(RawOrganization rawOrganization, Organization orgDest);

    RawOrganization getOne(Integer id);
}