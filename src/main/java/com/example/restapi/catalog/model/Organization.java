package com.example.restapi.catalog.model;

import javax.persistence.*;

@Entity
@Table(name="organization")

public class Organization {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id", columnDefinition ="INT", nullable = false)
    private Integer orgId;

    @Column(name="inn", columnDefinition ="INT(12)", nullable = false)
    private Integer inn;

    @Column(name="name", columnDefinition ="VARCHAR(60)", nullable = false)
    private String fullName;

    @Column(name="short_name", columnDefinition ="VARCHAR(30)", nullable = true)
    private String name;

    @Column(name="kpp", columnDefinition ="INT(9)")
    private Integer kpp;

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

    public Integer getKpp() {
        return kpp;
    }

    public void setKpp(Integer kpp) {
        this.kpp = kpp;
    }
}
