package com.example.restapi.catalog.model;

import javax.persistence.*;

@Entity
@Table(name="organization")

public class Organization {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "org_id", columnDefinition = "INT", nullable = false)
    private Integer orgId;

    @Column(name = "inn", columnDefinition = "INT(12)", nullable = false)
    private Integer inn;

    @Column(name = "full_name", columnDefinition = "VARCHAR(60)", nullable = false)
    private String fullNameOranization;

    @Column(name = "short_name", columnDefinition = "VARCHAR(30)", nullable = true)
    private String shortNameOranization;

    @Column(name = "kpp", columnDefinition = "INT(9)")
    private Integer kpp;

    public Organization(Integer orgId, Integer inn, String fullNameOranization, String shortNameOranization, Integer kpp) {
        this.orgId = orgId;
        this.inn = inn;
        this.fullNameOranization = fullNameOranization;
        this.shortNameOranization = shortNameOranization;
        this.kpp = kpp;
    }

    public Organization() {
    }

    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

    public Integer getInn() {
        return inn;
    }

    public void setInn(Integer inn) {
        this.inn = inn;
    }

    public String getFullNameOranization() {
        return fullNameOranization;
    }

    public void setFullNameOranization(String fullNameOranization) {
        this.fullNameOranization = fullNameOranization;
    }

    public String getShortNameOranization() {
        return shortNameOranization;
    }

    public void setShortNameOranization(String shortNameOranization) {
        this.shortNameOranization = shortNameOranization;
    }

    public Integer getKpp() {
        return kpp;
    }

    public void setKpp(Integer kpp) {
        this.kpp = kpp;
    }
}