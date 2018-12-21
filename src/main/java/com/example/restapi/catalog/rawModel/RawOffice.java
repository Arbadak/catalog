package com.example.restapi.catalog.rawModel;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class RawOffice {

    private Integer Id;

    private Integer orgId;


    private String name;

    private String address;

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
    //public void setActive(Boolean active) { isActive = active; }
    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }
}
