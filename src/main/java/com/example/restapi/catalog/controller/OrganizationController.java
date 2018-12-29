package com.example.restapi.catalog.controller;

import com.example.restapi.catalog.groups.GroupAdd;
import com.example.restapi.catalog.groups.GroupList;
import com.example.restapi.catalog.groups.GroupUpdate;
import com.example.restapi.catalog.model.Organization;
import com.example.restapi.catalog.rawModel.RawOrganization;
import com.example.restapi.catalog.rawModel.ResponceWrapper;
import com.example.restapi.catalog.rawModel.resultResponce;
import com.example.restapi.catalog.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/***
 *  Контроллер по запросам organization
 *
 */
@RestController
@RequestMapping("organization")
@Validated
public class OrganizationController {

    private final OrganizationService organizationService;

    @Autowired
    public OrganizationController(OrganizationService organizationService) {
        this.organizationService = organizationService;
    }

    /*** Обработка запросов по URI /organization/list
     *
     * @param rawOrganization
     * @return List организаций с укааным OrgId, либо пустой лист если организаций с такмим реквизитами не найдено
     */

    @PostMapping("/list")
    public ResponceWrapper orgList(@RequestBody @Validated({ GroupList.class }) RawOrganization rawOrganization) {
       return new ResponceWrapper(organizationService.getOrgList(rawOrganization));
    }
    /**
     * Обработка запросов organization/ID
     * @param id
     * @return Organization с указанным id, либо пустой результат если организации с таким ID не найдено
     */

    @GetMapping("{id}")
    @ResponseBody
    public ResponceWrapper org(@PathVariable("id") Integer id) {
        return new ResponceWrapper(organizationService.getOne(id));
    }

    /**
     * Добавление новой организации
     * @param rawOrganization
     * @return success, либо ошибка в случае если
     */
    @PostMapping("/save")
    public ResponceWrapper save(@RequestBody @Validated({ GroupAdd.class })RawOrganization rawOrganization) {
        return new ResponceWrapper(organizationService.add(rawOrganization));
    }

    /**
     * Обнволение параметров сущевствующей организации
     * @param organizationFromDB
     * @param organizationFromWeb
     * @return
     */
    @PostMapping("update/{id}")
    public resultResponce update(@PathVariable("id") Organization organizationFromDB, @RequestBody @Validated({ GroupUpdate.class }) RawOrganization organizationFromWeb) {
        return organizationService.update(organizationFromWeb, organizationFromDB);
    }
}