package com.example.restapi.catalog.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Version;

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

    @Version
    @Column(name = "OPTLOCK")
    private Integer optlock;

    @Column(name = "inn", length = 12, nullable = false)
    private String inn;

    @Column(name = "name", length = 60, nullable = false)
    private String fullName;

    @Column(name = "short_name", length = 30, nullable = true)
    private String name;

    @Column(name = "kpp", length = 9, nullable = false)
    private String kpp;

    public Organization() {
    }

    public Integer getOrgId() {
        return orgId;
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