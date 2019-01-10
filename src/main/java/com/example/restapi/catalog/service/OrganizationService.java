package com.example.restapi.catalog.service;

import com.example.restapi.catalog.model.Organization;
import com.example.restapi.catalog.rawmodel.RawOrganization;
import com.example.restapi.catalog.rawmodel.ResultResponse;

import java.util.List;

/**
 * Интерфейс с описанием методов для запросов вида /api/organization
 */
public interface OrganizationService {

    List<RawOrganization> getOrgList(RawOrganization rawOrganization);

    ResultResponse add(RawOrganization rawOrganization);

    ResultResponse update(RawOrganization rawOrganization, Organization orgDest);

    RawOrganization getOne(Integer id);
}