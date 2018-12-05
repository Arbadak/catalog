package com.example.restapi.catalog.controller;

import com.example.restapi.catalog.exceptions.NotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("office")
public class OfficeController {
    private int counter=5;
    private List<Map<String,String>> offices = new ArrayList<Map<String, String>>(){{
        add(new HashMap<String, String>(){{put("id","1"); put("address","Маяковского 7");}});
        add(new HashMap<String, String>(){{put("id","2"); put("address","Пушкина 40");}});
        add(new HashMap<String, String>(){{put("id","3"); put("address","Болотная 2");}});
        add(new HashMap<String, String>(){{put("id","4"); put("address","Григоровича 105");}});
    }};

    @GetMapping
    public List<Map<String,String>> list() {
        return  offices;
    }

    @GetMapping("{id}")
        public Map <String, String> getOne(@PathVariable String id){
     return getOffice(id);
    }

    private Map<String, String> getOffice(@PathVariable String id) {
        return offices.stream()
                .filter(offices -> offices.get("id").equals(id))
                .findFirst()
                .orElseThrow(NotFoundException::new);
    }

    @PostMapping("save")
    public Map<String, String> create(@RequestBody Map<String, String> office) {
      office.put("id", String.valueOf(counter++));
      offices.add(office);
      return office;
    }

    @PostMapping("update/{id}")
    public Map<String, String> update(@PathVariable String id, @RequestBody Map<String, String> office){
        Map <String, String> officeFromDb = getOffice(office.get("id"));
        officeFromDb.putAll(office);
        officeFromDb.put("id",id);
        return officeFromDb;
    }


}
