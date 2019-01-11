package com.example.restapi.catalog.controller;

import com.example.restapi.catalog.groups.GroupAdd;
import com.example.restapi.catalog.groups.GroupList;
import com.example.restapi.catalog.groups.GroupUpdate;
import com.example.restapi.catalog.rawmodel.RawOffice;
import com.example.restapi.catalog.rawmodel.ResultResponse;
import com.example.restapi.catalog.service.OfficeService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


/***
 * Рест контроллер запросов обьекта office
 */


@RestController
@RequestMapping("office")
public class OfficeController {

    private final OfficeService officeService;


    @Autowired
    public OfficeController(OfficeService officeService) {
        this.officeService = officeService;
    }

    /**
     * Метод для отображения списка пользователей по указанным параметрам организации
     *
     * @param office “orgId”:””, //обязательный параметр
     *               “name”:””,
     *               “phone”:””,
     *               “isActive”
     * @return RawOffice
     */
    @PostMapping(value = "/list", consumes =  APPLICATION_JSON_VALUE)
    public List<RawOffice> officeList(@RequestBody @Validated({GroupList.class}) RawOffice office) {
        return officeService.getOfficeList(office);
    }


    /**
     * Метод отображения данных офиса по указанному идентификатору офиса
     *
     * @param officeId - идентифиактор офиса
     * @return Список RawOffice
     */
    @GetMapping("{id}")
    public RawOffice getOne(@PathVariable("id") Integer officeId) {
        return officeService.getOne(officeId);
    }

    /**
     * Метод добавления нового офиса
     *
     * @param office “name”:””,
     *               “address”:””,
     *               “phone”,””,
     *               “isActive”:
     * @return "result:success", либо "error:XXXXXXXXXXXXX" в случае если ошибка
     */
    @PostMapping(value = "save", consumes =  APPLICATION_JSON_VALUE)
    public ResultResponse save(@RequestBody @Validated({GroupAdd.class}) RawOffice office) {
        return officeService.add(office);
    }

    /**
     * Метод изменения данных сущевствующего офиса
     *
     * @param office “id”:””, //обязательный параметр
     *               “name”:””, //обязательный параметр
     *               “address”:””, //обязательный параметр
     *               “phone”,””,
     *               “isActive”:
     * @return "result:success", либо "error:XXXXXXXXXXXXX" в случае если ошибка
     */
    @PostMapping(value = "update", consumes =  APPLICATION_JSON_VALUE)
    public ResultResponse update(@RequestBody @Validated({GroupUpdate.class}) RawOffice office) {
        return officeService.update(office);
    }
}
