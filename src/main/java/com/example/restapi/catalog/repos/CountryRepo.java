package com.example.restapi.catalog.repos;

import com.example.restapi.catalog.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepo extends JpaRepository<Country, Integer> {

    Country findByCitizenshipCode(String CitizenshipCode);
}