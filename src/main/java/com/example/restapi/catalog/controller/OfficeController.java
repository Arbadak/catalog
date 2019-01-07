package com.example.restapi.catalog.controller;

import com.example.restapi.catalog.groups.GroupAdd;
import com.example.restapi.catalog.groups.GroupList;
import com.example.restapi.catalog.groups.GroupUpdate;
import com.example.restapi.catalog.rawmodel.RawOffice;
import com.example.restapi.catalog.rawmodel.ResponceWrapper;
import com.example.restapi.catalog.service.OfficeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


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
     * @return
     */
    @PostMapping("/list")
    public ResponceWrapper officeList(@RequestBody @Validated({GroupList.class}) RawOffice office) {
        return new ResponceWrapper(officeService.getOfficeList(office));
    }


    /**
     * Метод отображения данных офиса по указанному идентификатору офиса
     *
     * @param officeId
     * @return
     */
    @GetMapping("{id}")
    public ResponceWrapper getOne(@PathVariable("id") Integer officeId) {
        return new ResponceWrapper(officeService.getOne(officeId));
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
    @PostMapping("save")
    public ResponceWrapper save(@RequestBody @Validated({GroupAdd.class}) RawOffice office) {
        return new ResponceWrapper(officeService.add(office));
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
    @PostMapping("update")
    public ResponceWrapper update(@RequestBody @Validated({GroupUpdate.class}) RawOffice office) {
        return new ResponceWrapper(officeService.update(office));
    }
}
