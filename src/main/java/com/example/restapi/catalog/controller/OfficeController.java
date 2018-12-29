package com.example.restapi.catalog.controller;

import com.example.restapi.catalog.groups.GroupAdd;
import com.example.restapi.catalog.groups.GroupList;
import com.example.restapi.catalog.groups.GroupUpdate;
import com.example.restapi.catalog.rawModel.RawOffice;
import com.example.restapi.catalog.rawModel.ResponceWrapper;
import com.example.restapi.catalog.service.OfficeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/***
 *
 */

@RestController
@RequestMapping("office")
public class OfficeController {

    private final OfficeService officeService;

    @Autowired
    public OfficeController(OfficeService officeService) {
        this.officeService = officeService;
    }


    @PostMapping("/list")
    public ResponceWrapper officeList(@RequestBody @Validated({ GroupList.class }) RawOffice office) {
        return new ResponceWrapper(officeService.getOfficeList(office));
    }


    @GetMapping("{id}")
    public ResponceWrapper getOne(@PathVariable("id") Integer officeId) {
        return new ResponceWrapper(officeService.getOne(officeId));
    }

    @PostMapping("save")
    public ResponceWrapper save(@RequestBody @Validated({ GroupAdd.class }) RawOffice office) {
        return new ResponceWrapper(officeService.add(office));
    }

    @PostMapping("update")
    public ResponceWrapper update(@RequestBody @Validated({ GroupUpdate.class }) RawOffice office) {
            return new ResponceWrapper(officeService.update(office));
    }
  /*
   //TODO Удалить

   @ExceptionHandler(com.example.restapi.catalog.exceptions.NotFoundException.class)
    public @ResponseBody
    ResponceWrapper handleNotFoundException(com.example.restapi.catalog.exceptions.NotFoundException e) {

        return new ResponceWrapper(new resultResponce(null,( e.getMessage())));
    }
    @ExceptionHandler(org.springframework.web.bind.MethodArgumentNotValidException.class)
    public @ResponseBody
    ResponceWrapper handleValidationException(org.springframework.web.bind.MethodArgumentNotValidException e) {

        return new ResponceWrapper(new resultResponce(null,( e.getBindingResult()
                .getAllErrors()
                .listIterator()
                .next()
                .getDefaultMessage() ) ));
    }*/



   }
