package com.example.restapi.catalog.service;

import com.example.restapi.catalog.rawModel.RawOffice;
import com.example.restapi.catalog.rawModel.ResultResponce;

import java.util.List;

/**
 * Интерфейс с описанием методов для запросов вида /api/office
 */
public interface OfficeService {

    List<RawOffice> getOfficeList(RawOffice rawOffice);

    RawOffice getOne(Integer officeId);

    ResultResponce add(RawOffice office);

    ResultResponce update(RawOffice office);
}
