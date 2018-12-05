package com.example.restapi.catalog.model;

import javax.persistence.*;

@Entity
@Table(name="office")
public class Office {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="office_id", columnDefinition ="INT",  nullable = false)
    private Integer officeId;

    /*@ManyToOne
    @JoinColumn(name = "organization_id")                   ************ DISABLED FOR DEV PURPOSE******
    private Organization organizationId ;*/

    @Column(name="office_name", columnDefinition ="VARCHAR(50)", nullable = false)
    private String officeName;

    @Column(name="address", columnDefinition ="VARCHAR(100)", nullable = false)
    private String address;

    @Column(name="phone", columnDefinition ="INT(11)", nullable = true)
    private Integer phoneOffice;

    @Column(name="is_active", columnDefinition ="BOOLEAN", nullable = true)
    private Boolean isActive;

    @Column(name="is_main", columnDefinition ="BOOLEAN", nullable = true)
    private Boolean isMain;


    public Office(Integer officeId, /*Organization organizationId,*/ String address, int phoneOffice, Boolean isActive, Boolean isMain) {
        this.officeId = officeId;
       // this.organizationId = organizationId;
        this.address = address;
        this.phoneOffice = phoneOffice;
        this.isActive = isActive;
        this.isMain = isMain;
    }

    public Office() {
    }

    public Integer getOfficeId() {return officeId;}
    public void setOfficeId(Integer officeId) {this.officeId = officeId;}

    //public Organization getOrganizationId() {return organizationId;}
    //public void setOrganizationId(Organization organizationId) {this.organizationId = organizationId;}

    public String getAddress() {return address;}
    public void setAddress(String address) {this.address = address;}

    public int getPhoneOffice() {return phoneOffice;}
    public void setPhoneOffice(Integer phoneOffice) {this.phoneOffice = phoneOffice;}

    public Boolean getActive() {return isActive;}
    public void setActive(Boolean active) {isActive = active;}

    public Boolean getMain() {return isMain;}
    public void setMain(Boolean main) {isMain = main;}

    public String getOfficeName() {return officeName;}
    public void setOfficeName(String officeName) {this.officeName = officeName;}
}
