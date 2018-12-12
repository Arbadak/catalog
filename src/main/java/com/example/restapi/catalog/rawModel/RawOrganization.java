package com.example.restapi.catalog.rawModel;

import com.example.restapi.catalog.model.Organization;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class RawOrganization {


    private Integer orgId;
    private String FullName;
    private String Name;
    private Integer inn;
    private Integer kpp;
    private String address;
    private Integer phone;
    private Boolean isActive;

    public RawOrganization() {
    }

    public RawOrganization(Integer orgId, String name, Boolean isActive) {
        this.orgId = orgId;
        Name = name;
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

    public Integer getInn() {
        return inn;
    }

    public void setInn(Integer inn) {
        this.inn = inn;
    }

    public Integer getKpp() {
        return kpp;
    }

    public void setKpp(Integer kpp) {
        this.kpp = kpp;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getPhone() {
        return phone;
    }

    public void setPhone(Integer phone) {
        this.phone = phone;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }
}
