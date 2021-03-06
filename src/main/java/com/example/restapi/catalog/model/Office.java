package com.example.restapi.catalog.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import javax.persistence.Version;

/**
 * Сущность для хранения данных "офис"
 */
@Entity
@Table(name = "office")
public class Office {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "INT")
    private Integer Id;

    @Version
    @Column(name = "OPTLOCK")
    private Integer optlock;

    @ManyToOne
    @JoinColumn(name = "organization_id", nullable = false)
    private Organization organization;

    @Column(name = "name", length = 50, nullable = false)
    private String name;

    @Column(name = "phone", length = 11, nullable = true)
    private String phone;

    @Column(name = "address", length = 100, nullable = false)
    private String address;

    @Column(name = "is_active", nullable = true)
    private Boolean isActive;

    @Column(name = "is_main", nullable = true)
    private Boolean isMain;

    public Office() {
    }

    public Office(Organization organization, String name, String address, String phone, Boolean isActive, Boolean isMain) {
        this.organization = organization;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.isActive = isActive;
        this.isMain = isMain;
    }

    public Integer getId() {
        return Id;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
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

    public void setIsActive(Boolean active) {
        isActive = active;
    }

    public Boolean getMain() {
        return isMain;
    }

    public void setMain(Boolean main) {
        isMain = main;
    }
}
