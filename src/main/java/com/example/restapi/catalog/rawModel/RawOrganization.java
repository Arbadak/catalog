package com.example.restapi.catalog.rawModel;

import com.example.restapi.catalog.groups.GroupAdd;
import com.example.restapi.catalog.groups.GroupList;
import com.example.restapi.catalog.groups.GroupUpdate;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/** Модель для получения данных от запросов типа Organization
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RawOrganization {

    @NotNull(message = "Не указан идентификатор организации", groups = {GroupUpdate.class})
    @Pattern(regexp = "\\d+", message = "Идентификатор организации указан не правильно", groups = {GroupUpdate.class})
    private Integer orgId;

    @NotNull(message = "Не указано полное имя организации", groups = {GroupAdd.class, GroupUpdate.class})
    @Size(min = 2, message = "Полное имя организации не может быть короче 2 символов", groups = {GroupAdd.class, GroupUpdate.class, GroupList.class})
    @Size(max = 60, message = "Полное имя организации не может быть длинее 60 символов", groups = {GroupAdd.class, GroupUpdate.class, GroupList.class})
    private String FullName;

    @NotNull(message = "Не указано имя организации", groups = {GroupAdd.class, GroupUpdate.class, GroupList.class})
    @Size(min = 2, message = "Имя организации не может быть короче 2 символов", groups = {GroupAdd.class, GroupUpdate.class, GroupList.class})
    @Size(max = 30, message = "Имя организации не может быть длинее 30 символов", groups = {GroupAdd.class, GroupUpdate.class, GroupList.class})
    private String Name;

    @NotNull(message = "Не указан ИНН", groups = {GroupAdd.class, GroupUpdate.class})
    @Pattern(regexp = "\\d{8,12}", message = "ИНН организации введен неверно", groups = {GroupAdd.class, GroupUpdate.class, GroupList.class})
    private String inn;

    @NotNull(message = "Не указан КПП", groups = {GroupAdd.class, GroupUpdate.class})
    @Pattern(regexp = "\\d{9}", message = "КПП организации введен неверно", groups = {GroupAdd.class, GroupUpdate.class})
    private String kpp;

    @NotNull(message = "Не указан адрес организации", groups = {GroupAdd.class, GroupUpdate.class})
    @Size(min = 2, message = "Адрес организации не может быть короче 2 символов", groups = {GroupAdd.class, GroupUpdate.class})
    @Size(max = 100, message = "Адрес организации не может быть длинее 100 символов", groups = {GroupAdd.class, GroupUpdate.class})
    private String address;

    @Pattern(regexp = "\\d{5,11}", message = "Телефон организации введен неверно", groups = {GroupAdd.class, GroupUpdate.class})
    private String phone;

    private Boolean isActive;

    public RawOrganization() {
    }

    public RawOrganization(Integer orgId, String name, Boolean isActive) {
        this.orgId = orgId;
        Name = name;
        this.isActive = isActive;
    }

    /**
     * For testing purpose - all fileds constructor
     *
     * @return
     */
    public RawOrganization(String fullName, String name, String inn, String kpp, String address, String phone, Boolean isActive) {
        FullName = fullName;
        Name = name;
        this.inn = inn;
        this.kpp = kpp;
        this.address = address;
        this.phone = phone;
        this.isActive = isActive;
    }


    public Integer getId() {
        return orgId;
    }

    public void setId(Integer id) {
        this.orgId = orgId;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String FullName) {
        this.FullName = FullName;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getInn() {
        return inn;
    }

    public void setInn(String inn) {
        this.inn = inn;
    }

    public String getKpp() {
        return kpp;
    }

    public void setKpp(String kpp) {
        this.kpp = kpp;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }
}
