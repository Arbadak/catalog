package com.example.restapi.catalog.repos;

import com.example.restapi.catalog.model.Office;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OfficeRepo extends JpaRepository<Office, Integer> {
}
