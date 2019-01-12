package com.example.restapi.catalog.rawmodel;

import com.example.restapi.catalog.groups.GroupAdd;
import com.example.restapi.catalog.groups.GroupList;
import com.example.restapi.catalog.groups.GroupUpdate;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/** Модель для получения данных от запросов типа Office
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RawOffice {


    @NotNull(message = "Не указан идентификатор организации", groups = {GroupUpdate.class})
    //@Pattern(regexp = "\\d+", message = "Идентификатор организации указан не правильно",  groups = {GroupAdd.class, GroupUpdate.class})
    private Integer Id;

    @NotNull(message = "Не указан идентификатор организации", groups = {GroupAdd.class, GroupList.class})
    //@Pattern(regexp = "\\d+", message = "Идентификатор организации указан не правильно",  groups = {GroupAdd.class, GroupUpdate.class, GroupList.class})
    private Integer orgId;

    @NotNull(message = "Не указано имя организации", groups = {GroupUpdate.class})
    @Size(min = 2, message = "Имя организации не может быть короче 2 символов", groups = {GroupAdd.class, GroupUpdate.class, GroupList.class})
    @Size(max = 30, message = "Имя организации не может быть длинее 30 символов", groups = {GroupAdd.class, GroupUpdate.class, GroupList.class})
    private String name;

    @NotNull(message = "Не указан адрес организации", groups = {GroupUpdate.class})
    @Size(min = 2, message = "Адрес организации не может быть короче 2 символов", groups = {GroupAdd.class, GroupUpdate.class})
    @Size(max = 100, message = "Адрес организации не может быть длинее 100 символов", groups = {GroupAdd.class, GroupUpdate.class})
    private String address;

    @Pattern(regexp = "\\d{5,11}", message = "Телефон организации введен неверно", groups = {GroupAdd.class, GroupUpdate.class})
    private String phone;

    private Boolean isActive;

    public RawOffice() {
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    @Override
    public String toString() {
        return "RawOffice{" +
                "Id=" + Id +
                ", orgId=" + orgId +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", isActive=" + isActive +
                '}';
    }
}
