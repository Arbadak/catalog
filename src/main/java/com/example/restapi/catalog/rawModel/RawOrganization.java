package com.example.restapi.catalog.rawModel;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.validation.constraints.NotEmpty;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class RawOrganization {


    private Integer orgId;
    private String FullName;
    //@NotNull(message = "Требуется название организации", groups = OrgListValidator.class)
    @NotEmpty(message = "name cannot be null")
    private String Name;

    private String inn;
    private String kpp;
    private String address;
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
