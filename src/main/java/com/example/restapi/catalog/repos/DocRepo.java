package com.example.restapi.catalog.repos;

import com.example.restapi.catalog.model.Doc;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocRepo extends JpaRepository <Doc, Integer> {
}
