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

    /** Обработчик ошибок маппинга парамтров на модель
     *
     * @param e
     * @return error:неверный тип данных
     */

    ///TODO Эту всю радость бы в контроллер адвайс...
    /// При неверноем типе поля будет выдаваться ошибка
    @ExceptionHandler(com.fasterxml.jackson.databind.exc.InvalidFormatException.class)
    public @ResponseBody
    ResponceWrapper handleDeserializeException(com.fasterxml.jackson.databind.exc.InvalidFormatException e) {

        return new ResponceWrapper(new resultResponce(null, "неверный тип данных"));
    }
    @ExceptionHandler(org.springframework.web.bind.MethodArgumentNotValidException.class)
        public @ResponseBody
        ResponceWrapper handleValidationException(org.springframework.web.bind.MethodArgumentNotValidException e) {

            return new ResponceWrapper(new resultResponce(null,( e.getBindingResult()
                                                                            .getAllErrors()
                                                                            .listIterator()
                                                                            .next()
                                                                            .getDefaultMessage() ) ));
    }

    /**
     * Обработка запросов organization/ID
     *
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
     *
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