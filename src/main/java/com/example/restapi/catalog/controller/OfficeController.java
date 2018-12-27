package com.example.restapi.catalog.controller;

import com.example.restapi.catalog.model.Office;
import com.example.restapi.catalog.rawModel.RawOffice;
import com.example.restapi.catalog.rawModel.ResponceWrapper;
import com.example.restapi.catalog.rawModel.resultResponce;
import com.example.restapi.catalog.service.OfficeService;
import org.aspectj.lang.annotation.AdviceName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.regex.Pattern;

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
    public ResponceWrapper officeList(@RequestBody RawOffice office) {
        if (office.getPhone().length() > 11 || office.getName().length() > 50 ) {
            new resultResponce(null, "превышена длинна значения");
        }

        return new ResponceWrapper(officeService.getOfficeList(office));
    }


    @GetMapping("{id}")
    public ResponceWrapper getOne(@PathVariable("id") Integer officeId) {
        return new ResponceWrapper(officeService.getOne(officeId));
    }

    @PostMapping("save")
    public ResponceWrapper save(@RequestBody RawOffice office) {
        if (office.getPhone().length() > 11 || office.getName().length() > 50 || office.getAddress().length() > 100) {
            new resultResponce(null, "превышена длинна значения");
        }
        if (!(Pattern.compile("\\d{11}").matcher(office.getPhone())).find()) {
            return new ResponceWrapper(new resultResponce(null, "номер телефон должен содержать только цифры"));
        }
        return new ResponceWrapper(officeService.add(office));
    }

    @PostMapping("update")
    public ResponceWrapper update(@RequestBody RawOffice office) {
        if (office.getPhone().length() > 11 || office.getName().length() > 50 || office.getAddress().length() > 100) {
            new resultResponce(null, "превышена длинна значения");
        }
        if (!(Pattern.compile("\\d{11}").matcher(office.getPhone())).find()) {
            return new ResponceWrapper(new resultResponce(null, "номер телефон должен содержать только цифры"));
        }
        return new ResponceWrapper(officeService.update(office));
    }
   }
