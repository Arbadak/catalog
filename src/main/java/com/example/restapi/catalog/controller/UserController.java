package com.example.restapi.catalog.controller;

import com.example.restapi.catalog.groups.GroupAdd;
import com.example.restapi.catalog.groups.GroupList;
import com.example.restapi.catalog.groups.GroupUpdate;
import com.example.restapi.catalog.rawmodel.RawUser;
import com.example.restapi.catalog.rawmodel.ResponceWrapper;
import com.example.restapi.catalog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

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
    @PostMapping("/list")
    public ResponceWrapper userList(@RequestBody @Validated({GroupList.class}) RawUser user) {
        return new ResponceWrapper(userService.getUserList(user));
    }

    /**
     * Метод отображения данных польщователя по указанному идентификатору пользователя
     *
     * @param userId
     * @return данные пользоватял либо пустой обьект если пользщователь не найден
     */
    @GetMapping("{id}")
    public ResponceWrapper getOne(@PathVariable("id") Integer userId) {
        return new ResponceWrapper(userService.getOne(userId));
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
    @PostMapping("save")
    public ResponceWrapper save(@RequestBody @Validated({GroupAdd.class}) RawUser user) {
        return new ResponceWrapper(userService.add(user));
    }

    /**
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
    @PostMapping("update")
    public ResponceWrapper update(@RequestBody @Validated({GroupUpdate.class}) RawUser user, BindingResult result) {
//TODO проверить нужно ли
        /*
        if (result.hasErrors()) {
            return new ResponceWrapper(new ResultResponce(null, (result.getFieldError().getDefaultMessage())));
        }*/
        return new ResponceWrapper(userService.update(user));
    }
}