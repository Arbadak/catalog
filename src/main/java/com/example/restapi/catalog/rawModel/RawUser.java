package com.example.restapi.catalog.rawModel;

import com.example.restapi.catalog.groups.GroupAdd;
import com.example.restapi.catalog.groups.GroupList;
import com.example.restapi.catalog.groups.GroupUpdate;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;


public class RawUser {
    @NotNull (message = "Не указан id", groups = {GroupUpdate.class})
    private Integer id;

    @NotNull (message = "Не указано имя", groups = {GroupAdd.class})
    @Pattern(regexp = "\\D{2,15}", message = "Ошибка в поле имя",  groups = {GroupAdd.class, GroupUpdate.class, GroupList.class} )
    private String firstName;
    @Pattern(regexp = "\\D{2,15}", message = "Ошибка в поле отчество",  groups = {GroupAdd.class, GroupUpdate.class})
    private String secondName;
    @Pattern(regexp = "\\D{2,15}", message = "Ошибка в поле фамилия",  groups = {GroupAdd.class, GroupUpdate.class})
    private String lastName;

    @NotNull  (message = "Поле должность должно быть указано",groups = {GroupAdd.class,GroupUpdate.class})
    private String position;

    @Pattern(regexp = "\\d{5,11}", message = "Ошибка в поле телефон",  groups = {GroupAdd.class, GroupUpdate.class})
    private String phone;

    @Pattern(regexp = "\\D{5,115}", message = "Ошибка в поле документ",  groups = {GroupAdd.class, GroupUpdate.class})
    private String docName;

    @Pattern(regexp = "\\d{10}", message = "Ошибка в поле номер документа",  groups = {GroupAdd.class, GroupUpdate.class})
    private String docNumber;

    private LocalDate docDate;

    @Pattern(regexp = "\\d{2}", message = "Ошибка в поле код документа",  groups = {GroupAdd.class})
    private String docCode;

    @Pattern(regexp = "\\D{3,60}", message = "Ошибка в поле наименование гражданства",  groups = {GroupAdd.class, GroupUpdate.class})
    private String citizenshipName;

    @Pattern(regexp = "\\d{3}", message = "Ошибка в поле код страны",  groups = {GroupAdd.class, GroupUpdate.class})
    private String citizenshipCode;

    private Boolean isIdentified;

    @NotNull  (message = "Поле officeId должно быть указано",groups = {GroupList.class})
    private Integer officeId;


    public RawUser() {
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

    public String getDocName() { return docName; }

    public void setDocName(String docName) {
        this.docName = docName;
    }

    public String getDocNumber() {
        return docNumber;
    }

    public void setDocNumber(String docNumber) {
        this.docNumber = docNumber;
    }

    public LocalDate getDocDate() {
        return docDate;
    }

    public void setDocDate(LocalDate docDate) {
        this.docDate = docDate;
    }


    public String getDocCode() {
        return docCode;
    }

    public void setDocCode(String docCode) {
        this.docCode = docCode;
    }

    public String getCitizenshipName() {
        return citizenshipName;
    }

    public void setCitizenshipName(String citizenshipName) {
        this.citizenshipName = citizenshipName;
    }

    public String getCitizenshipCode() {
        return citizenshipCode;
    }

    public void setCitizenshipCode(String citizenshipCode) {
        this.citizenshipCode = citizenshipCode;
    }

    public Boolean getIsIdentified() {
        return isIdentified;
    }

    public void setIsIdentified(Boolean isIdentified) {
        this.isIdentified = isIdentified;
    }

    public Integer getOfficeId() {
        return officeId;
    }

    public void setOfficeId(Integer officeId) {
        this.officeId = officeId;
    }
}
