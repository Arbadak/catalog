package com.example.restapi.catalog.repos;

import com.example.restapi.catalog.model.DocData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocDataRepo extends JpaRepository<DocData, Integer> {
}
