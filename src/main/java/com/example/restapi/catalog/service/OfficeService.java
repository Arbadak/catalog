package com.example.restapi.catalog.service;

import com.example.restapi.catalog.rawmodel.RawOffice;
import com.example.restapi.catalog.rawmodel.ResultResponse;

import java.util.List;

/**
 * Интерфейс с описанием методов для запросов вида /api/office
 */
public interface OfficeService {

    List<RawOffice> getOfficeList(RawOffice rawOffice);

    RawOffice getOne(Integer officeId);

    ResultResponse add(RawOffice office);

    ResultResponse update(RawOffice office);
}
