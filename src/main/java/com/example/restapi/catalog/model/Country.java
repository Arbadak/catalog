package com.example.restapi.catalog.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

/**
 * Вспомогательная сущность-справочник для данных"гражданство"
 */
@Entity
@Table(name = "country")
public class Country {

    @Id
    @Column(name = "id", columnDefinition = "INT")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer citizinshipId;

    @Column(name = "code", length = 3, nullable = false)
    private String citizenshipCode;

    @Column(name = "name", length = 60, nullable = false)
    private String citizenshipName;

    public Country() {
    }

    public Integer getCitizinshipId() {
        return citizinshipId;
    }

    public String getCitizenshipCode() {
        return citizenshipCode;
    }

    public void setCitizenshipCode(String citizenshipCode) {
        this.citizenshipCode = citizenshipCode;
    }

    public String getCitizenshipName() {
        return citizenshipName;
    }

    public void setCitizenshipName(String citizenshipName) {
        this.citizenshipName = citizenshipName;
    }
}