package com.example.restapi.catalog.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

/**
 * Сущность для хренения данных "организация"
 */
@Entity
@Table(name = "organization")
public class Organization {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "INT")
    private Integer orgId;

    @Column(name = "inn", columnDefinition = "VARCHAR(12)", nullable = false)
    private String inn;

    @Column(name = "name", columnDefinition = "VARCHAR(60)", nullable = false)
    private String fullName;

    @Column(name = "short_name", columnDefinition = "VARCHAR(30)", nullable = true)
    private String name;

    @Column(name = "kpp", columnDefinition = "VARCHAR(9)")
    private String kpp;

    public Organization() {
    }


    public Integer getOrgId() {
        return orgId;
    }

    private void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

    public String getInn() {
        return inn;
    }

    public void setInn(String inn) {
        this.inn = inn;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKpp() {
        return kpp;
    }

    public void setKpp(String kpp) {
        this.kpp = kpp;
    }
}
