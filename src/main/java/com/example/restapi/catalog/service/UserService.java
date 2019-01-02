package com.example.restapi.catalog.service;

import com.example.restapi.catalog.rawModel.RawUser;
import com.example.restapi.catalog.rawModel.ResultResponce;

import java.util.List;

/**
 * Интерфейс с описанием методов для запросов вида /api/user
 */
public interface UserService {

    List<RawUser> getUserList(RawUser rawUser);

    RawUser getOne(Integer id);

    ResultResponce add(RawUser rawUser);

    ResultResponce update(RawUser rawUser);
}
