package com.example.restapi.catalog.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.OneToOne;

/**Сущность для хранения данных "пользователь"
 *
 */
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", columnDefinition = "INT", nullable = false)
    private Integer id;

    @Column(name = "first_name", columnDefinition = "VARCHAR(15)", nullable = false)
    private String firstName;

    @Column(name = "second_name", columnDefinition = "VARCHAR(15)", nullable = true)
    private String secondName;

    @Column(name = "last_name", columnDefinition = "VARCHAR(15)", nullable = true)
    private String lastName;

    @Column(name = "position", columnDefinition = "VARCHAR(30)", nullable = false)
    private String position;

    @Column(name = "phone", columnDefinition = "INT(11)", nullable = true)
    private String phone;

    @OneToOne
    private DocData document;

    @OneToOne
    private Country citizenship;

    @Column(name = "is_identified", columnDefinition = "BOOLEAN", nullable = true)
    private Boolean isIdentified;

    @OneToOne
    private Office officeEmp;

    public User() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public DocData getDocument() {
        return document;
    }

    public void setDocument(DocData document) {
        this.document = document;
    }

    public Country getCitizenship() {
        return citizenship;
    }

    public void setCitizenship(Country citizenship) {
        this.citizenship = citizenship;
    }

    public Boolean getIsIdentified() {
        return isIdentified;
    }

    public void setIsIdentified(Boolean isIdentified) {
        this.isIdentified = isIdentified;
    }

    public Office getOfficeEmp() {
        return officeEmp;
    }

    public void setOfficeEmp(Office officeEmp) {
        this.officeEmp = officeEmp;
    }
}
