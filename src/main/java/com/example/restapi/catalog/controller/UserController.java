package com.example.restapi.catalog.controller;

import com.example.restapi.catalog.groups.GroupAdd;
import com.example.restapi.catalog.rawModel.RawUser;
import com.example.restapi.catalog.rawModel.ResponceWrapper;
import com.example.restapi.catalog.rawModel.resultResponce;
import com.example.restapi.catalog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("/list")
    public ResponceWrapper UserList(@RequestBody RawUser user) {

        return new ResponceWrapper(userService.getUserList(user));
    }


    @GetMapping("{id}")
    //public User getOne(@PathVariable("id") User user){  return user; }
    public ResponceWrapper getOne(@PathVariable("id") Integer userId) {

        return new ResponceWrapper(userService.getOne(userId));
    }


  @PostMapping("save")
    public ResponceWrapper save(@RequestBody RawUser user) {

        return new ResponceWrapper(userService.add(user));
    }

    @PostMapping("update")
    public ResponceWrapper update(@RequestBody @Validated({ GroupAdd.class }) RawUser user, BindingResult result) {

        if(result.hasErrors()) {
            return new ResponceWrapper (new resultResponce(null,(result.getFieldError().getDefaultMessage())));
        }
        return new ResponceWrapper(userService.update(user));
    }
}