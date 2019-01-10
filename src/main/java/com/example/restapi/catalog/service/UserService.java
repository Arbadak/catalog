package com.example.restapi.catalog.service;

import com.example.restapi.catalog.rawmodel.RawUser;
import com.example.restapi.catalog.rawmodel.ResultResponse;

import java.util.List;

/**
 * Интерфейс с описанием методов для запросов вида /api/user
 */
public interface UserService {

    List<RawUser> getUserList(RawUser rawUser);

    RawUser getOne(Integer id);

    ResultResponse add(RawUser rawUser);

    ResultResponse update(RawUser rawUser);
}
