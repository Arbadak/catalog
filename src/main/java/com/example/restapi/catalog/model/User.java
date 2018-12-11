package com.example.restapi.catalog.model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name="user")
public class User{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id", columnDefinition ="INT", nullable = false)
    private Integer id;

    @Column(name="first_name", columnDefinition ="VARCHAR(15)", nullable = false)
    private String firstName;

    @Column(name="second_name", columnDefinition ="VARCHAR(15)", nullable = true)
    private String secondName;

    @Column(name="last_name", columnDefinition ="VARCHAR(15)", nullable = true)
    private String lastName;

    @Column(name="position", columnDefinition ="VARCHAR(30)", nullable = false)
    private String position;

    @Column(name="phone", columnDefinition ="INT(11)", nullable = true)
    private Integer phone;

    @OneToOne
    //@JoinColumn(name = "id")
    //@Column(name="document")
    private DocData document;

    @OneToOne
    //@JoinColumn(name = "id")
    //@Column(name="citizenship")
    private Country citizenship;

    @Column(name="is_identified", columnDefinition ="BOOLEAN", nullable = true)
    private Boolean isIdentified;

    @OneToOne
    //@Column(name="office_emp")
    //@JoinColumn(name = "id")
    private Office officeEmp;

    public User() {
    }

    public Integer getUserId() {
        return id;
    }

    public void setUserId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Integer getUserPhone() {
        return phone;
    }

    public void setUserPhone(Integer phone) {
        this.phone = phone;
    }

    public DocData getUserDocument() {
        return document;
    }

    public void setUserDocument(DocData document) {
        this.document = document;
    }

    public Country getCitizenshipCountry() {
        return citizenship;
    }

    public void setCitizenshipCountry(Country citizenship) {
        this.citizenship = citizenship;
    }

    public Boolean getIdentified() {
        return isIdentified;
    }

    public void setIdentified(Boolean identified) {
        isIdentified = identified;
    }

    public Office getBindedOffice() {
        return officeEmp;
    }

    public void setBindedOffice(Office officeEmp) {
        this.officeEmp = officeEmp;
    }
}
