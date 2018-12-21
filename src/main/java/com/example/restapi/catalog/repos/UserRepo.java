package com.example.restapi.catalog.repos;

import com.example.restapi.catalog.model.Office;
import com.example.restapi.catalog.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepo extends JpaRepository<User, Integer> {

    List<User> findAllByOfficeEmp(Office officeId);

}
