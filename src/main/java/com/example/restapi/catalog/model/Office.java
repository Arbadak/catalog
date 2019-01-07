package com.example.restapi.catalog.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;

/** Сущность для хранения данных "офис"
 *
 */
@Entity
@Table(name = "office")
public class Office {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "INT")
    private Integer Id;

    @ManyToOne
    @JoinColumn(name = "organization_id", nullable = false)
    private Organization orgId;

    @Column(name = "name", columnDefinition = "VARCHAR(50)", nullable = false)
    private String name;

    @Column(name = "address", columnDefinition = "VARCHAR(100)", nullable = false)
    private String address;

    @Column(name = "phone", columnDefinition = "VARCHAR(11)", nullable = true)
    private String phone;

    @Column(name = "is_active", columnDefinition = "BOOLEAN", nullable = true)
    private Boolean isActive;

    @Column(name = "is_main", columnDefinition = "BOOLEAN", nullable = true)
    private Boolean isMain;

    public Office() {
    }

    public Office(Organization orgId, String name, String address, String phone, Boolean isActive, Boolean isMain) {
        this.orgId = orgId;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.isActive = isActive;
        this.isMain = isMain;
    }

    public Office(Integer Id, Organization orgId, String name, String address, String phone, Boolean isActive, Boolean isMain) {
        this.Id = Id;
        this.orgId = orgId;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.isActive = isActive;
        this.isMain = isMain;
    }


    public Integer getId() {
        return Id;
    }

    private void setId(Integer id) {
        this.Id = id;
    }

    public Organization getOrganizationId() {
        return orgId;
    }

    public void setOrganizationId(Organization orgId) {
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
