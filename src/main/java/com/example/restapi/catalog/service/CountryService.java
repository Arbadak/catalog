package com.example.restapi.catalog.service;

import com.example.restapi.catalog.model.Country;
import com.example.restapi.catalog.repos.CountryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CountryService {

    private final CountryRepo countryRepo;

@Autowired
    public CountryService(CountryRepo countryRepo) {
        this.countryRepo=countryRepo;
    }

    public List<Country>countries(){
        List<Country>all=countryRepo.findAll();
     return all;
    }

}
