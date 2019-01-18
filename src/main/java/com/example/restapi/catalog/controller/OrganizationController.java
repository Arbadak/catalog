package com.example.restapi.catalog.controller;

import com.example.restapi.catalog.groups.GroupAdd;
import com.example.restapi.catalog.groups.GroupList;
import com.example.restapi.catalog.groups.GroupUpdate;
import com.example.restapi.catalog.model.Organization;
import com.example.restapi.catalog.rawmodel.RawOrganization;
import com.example.restapi.catalog.rawmodel.ResultResponse;
import com.example.restapi.catalog.service.OrganizationService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;


import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 *  Рест контроллер запросов обьекта organization
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

    /**
     * Метод для отображения списка организаций по укащанным параметрам организации
     * @param rawOrganization   “name”:””, //обязательный параметр
     *                          “inn”:””,
     *                          “isActive”:””
     * @return List организаций с укааным OrgId, либо пустой лист если организаций с такмим реквизитами не найдено
     */
    @PostMapping(value = "/list", consumes = APPLICATION_JSON_VALUE)
    public List<RawOrganization> orgList(@RequestBody @Validated({GroupList.class}) RawOrganization rawOrganization) {
        return organizationService.getOrgList(rawOrganization);
    }

    /**
     * Метод отображения организации по указанному идентификатору организации
     *
     * @param id URI с номером организации
     * @return Organization с указанным id, либо пустой результат если организации с таким ID не найдено
     */
    @GetMapping("{id}")
    @ResponseBody
    public RawOrganization org(@PathVariable("id") Integer id) {
        return organizationService.getOne(id);
    }

    /**
     * Метод добавления новой организации
     * @param rawOrganization “name”:””, //обязательный параметр
     *                        “fullName”:””, //обязательный параметр
     *                        “inn”:””, //обязательный параметр
     *                        “kpp”:””, //обязательный параметр
     *                        “address”:””, //обязательный параметр
     *                        “phone”,””,
     *                        “isActive”:
     * @return "result:success", либо "error:XXXXXXXXXXXXX" в случае если ошибка
     */
    @PostMapping(value = "/save", consumes = APPLICATION_JSON_VALUE)
    public ResultResponse save(@RequestBody @Validated({GroupAdd.class}) RawOrganization rawOrganization) {
        return organizationService.add(rawOrganization);
    }

    /**
     * Метод обновления данных сущевствующей организации
     * @param organizationFromDB  id
     * @param organizationFromWeb “id”:””, //обязательный параметр
     *                            “name”:””, //обязательный параметр
     *                            “fullName”:””, //обязательный параметр
     *                            “inn”:””, //обязательный параметр
     *                            “kpp”:””,  //обязательный параметр
     *                            “address”:””, //обязательный параметр
     *                            “phone”,””,
     *                            “isActive”:
     * @return s"result:success", либо "error:XXXXXXXXXXXXX" в случае если ошибка
     */
    @PostMapping(value = "update/{id}", consumes = APPLICATION_JSON_VALUE)
    public ResultResponse update(@PathVariable("id") Organization organizationFromDB, @RequestBody @Validated({GroupUpdate.class}) RawOrganization organizationFromWeb) {
        return organizationService.update(organizationFromWeb, organizationFromDB);
    }
}