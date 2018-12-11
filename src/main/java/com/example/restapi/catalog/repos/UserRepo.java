package com.example.restapi.catalog.repos;

import com.example.restapi.catalog.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository <User, Integer> {

}
