package com.example.restapi.catalog.controller;

import com.example.restapi.catalog.model.Organization;
import com.example.restapi.catalog.rawModel.RawOrganization;
import com.example.restapi.catalog.rawModel.ResponceWrapper;
import com.example.restapi.catalog.rawModel.resultResponce;
import com.example.restapi.catalog.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/***
 *  этот контроллер недоделан и будет дополнен
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

    /// api/organization/list

    /*** Обработка запросов по URI /organization/list
     *
     * @param rawOrganization
     * @return List организаций с укааным OrgId, либо пустой лист если организаций с такмим реквизитами не найдено
     */

    @PostMapping("/list")
    public ResponceWrapper orgList(@RequestBody RawOrganization rawOrganization) {
        if (rawOrganization.getName() == null || rawOrganization.getName().length() > 30) { // Отсекаем пустое и слишком длинные имена
            return new ResponceWrapper(new resultResponce(null, "Ошибка поле Name не может быть пустым или длинее 30 симолов"));
        }
        if (rawOrganization.getInn() != null) {   ///Если нам сунули инн
            Matcher m = Pattern.compile("\\d{12}").matcher(rawOrganization.getInn());
            if (rawOrganization.getInn().length() != 12 || !m.find()) {  // отсекаем инн неравный 12 цифрам
                return new ResponceWrapper(new resultResponce(null, "инн введен неверно"));
            }
        }
        return new ResponceWrapper(organizationService.getOrgList(rawOrganization));
    }

    /** Обработчик ошибок маппинга парамтров на модель
     *
     * @param e
     * @return error:неверный тип данных
     */

  /*  /// При неверноем типе поля будет выдаваться ошибка
    @ExceptionHandler(com.fasterxml.jackson.databind.exc.InvalidFormatException.class)
    public @ResponseBody
    ResponceWrapper handleException(com.fasterxml.jackson.databind.exc.InvalidFormatException e) {
        return new ResponceWrapper(new resultResponce(null, "неверный тип данных"));
    }*/

    /** Обработка запросов organization/ID
     *
      * @param id
     * @return Organization с указанным id, либо пустой результат если организации с таким ID не найдено
     */

    @GetMapping("{id}")
    //public RawOrganization org(@PathVariable("id") Integer id){return organizationService.getOne(id); }
    @ResponseBody public ResponceWrapper org(@PathVariable("id") Integer id) {
        return new ResponceWrapper(organizationService.getOne(id));
    }

    /** Добавление новой организации
     *
     * @param rawOrganization
     * @return success, либо ошибка в случае если
     */
    @PostMapping("/save")
    public ResponceWrapper save(@RequestBody RawOrganization rawOrganization) {

        // проверяем что все обязательные поля заполнены
        if (rawOrganization.getInn() == null ||
                rawOrganization.getName() == null ||
                rawOrganization.getFullName() == null ||
                rawOrganization.getKpp() == null ||
                rawOrganization.getAddress() == null ||
                rawOrganization.getIsActive() == null) {
            return new ResponceWrapper(new resultResponce(null, "не указан обязательный параметр"));
        }
        //проверяем инн на корректность
        Matcher m = Pattern.compile("\\d{12}").matcher(rawOrganization.getInn());
        if (rawOrganization.getInn().length() != 12 || !m.find()) {  // отсекаем инн неравный 12 цифрам
            return new ResponceWrapper(new resultResponce(null, "инн введен неверно"));
        }
        // проверяем кпп на корректность
        m = Pattern.compile("\\d{9}").matcher(rawOrganization.getKpp());
        if (rawOrganization.getKpp().length() != 9 || !m.find()) {  // отсекаем кпп неравный 9 цифрам
            return new ResponceWrapper(new resultResponce(null, "кпп введен неверно"));
        }
        // проверяем на превышение длинны поля
        if (rawOrganization.getName().length() > 30 || rawOrganization.getFullName().length() > 60 || rawOrganization.getAddress().length() > 100) {
            return new ResponceWrapper(new resultResponce(null, "превышена длинна значения"));
        }

        return new ResponceWrapper(organizationService.add(rawOrganization));

    }

    @PostMapping("update/{id}")
    public resultResponce update(
            @PathVariable("id") Organization organizationFromDB,
            @RequestBody RawOrganization organizationFromWeb) {
        return organizationService.update(organizationFromWeb, organizationFromDB);
    }


}
