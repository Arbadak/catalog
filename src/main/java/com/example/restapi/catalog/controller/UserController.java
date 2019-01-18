package com.example.restapi.catalog.controller;

import com.example.restapi.catalog.groups.GroupAdd;
import com.example.restapi.catalog.groups.GroupList;
import com.example.restapi.catalog.groups.GroupUpdate;
import com.example.restapi.catalog.rawmodel.RawUser;
import com.example.restapi.catalog.rawmodel.ResultResponse;
import com.example.restapi.catalog.service.UserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * Рест контроллер запросов обьекта user
 */
@RestController
@RequestMapping("user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Метод для отображения списка пользователей по указанным параметрам пользователя
     *
     * @param user “officeId”:””, //обязательный параметр
     *             “firstName”:””,
     *             “lastName”:””,
     *             “middleName”:””,
     *             “position”,””,
     *             “docCode”:””,
     *             “citizenshipCode”:””
     * @return Список пользователя, либо пустой список если пользователи не найдены
     */
    @PostMapping(value = "/list", consumes = APPLICATION_JSON_VALUE)
    public List<RawUser> userList(@RequestBody @Validated({GroupList.class}) RawUser user) {
        return userService.getUserList(user);
    }

    /**
     * Метод отображения данных польщователя по указанному идентификатору пользователя
     *
     * @param userId - идентификатор пользователя
     * @return данные пользователя либо пустой обьект если пользщователь не найден
     */
    @GetMapping("{id}")
    public RawUser getOne(@PathVariable("id") Integer userId) {
        return userService.getOne(userId);
    }

    /**
     * Метод добавления нового пользователя
     *
     * @param user “firstName”:””, //обязательный параметр
     *             “secondName”:””,
     *             “middleName”:””,
     *             “position”:”” //обязательный параметр
     *             “phone”,””,
     *             “docCode”:””,
     *             “docName”:””,
     *             “docNumber”:””,
     *             “docDate”:””,
     *             “citizenshipCode”:””,
     *             “isIdentified”:
     * @return "result:success", либо "error:XXXXXXXXXXXXX" в случае если ошибка
     */
    @PostMapping(value = "save", consumes = APPLICATION_JSON_VALUE)
    public ResultResponse save(@RequestBody @Validated({GroupAdd.class}) RawUser user) {
        return userService.add(user);
    }

    /**
     * Метод изменения сущевствующего пользователя
     * @param user “id”:””, //обязательный параметр
     *             “firstName”:””, //обязательный параметр
     *             “secondName”:””,
     *             “middleName”:””,
     *             “position”:”” //обязательный параметр
     *             “phone”,””,
     *             “docName”:””,
     *             “docNumber”:””,
     *             “docDate”:””,
     *             “citizenshipCode”:””,
     *             “isIdentified”:
     * @return "result:success", либо "error:XXXXXXXXXXXXX" в случае если ошибка
     */
    @PostMapping(value = "update", consumes = APPLICATION_JSON_VALUE)
    public ResultResponse update(@RequestBody @Validated({GroupUpdate.class}) RawUser user, BindingResult result) {

        return userService.update(user);
    }
}