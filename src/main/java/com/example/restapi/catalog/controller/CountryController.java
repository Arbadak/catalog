package com.example.restapi.catalog.controller;

import com.example.restapi.catalog.model.Country;
import com.example.restapi.catalog.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("country")
public class CountryController {
    private final CountryService countryService;

    @Autowired
    public CountryController (CountryService countryService) {this.countryService=countryService;}

    ///Get list of countries
    @GetMapping
    public List<Country> countries (){return countryService.countries();}

}
