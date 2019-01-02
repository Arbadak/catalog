package com.example.restapi.catalog.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.OneToOne;
import java.time.LocalDate;


@Entity
@Table(name = "doc_data")
public class DocData {

    @Id
    @Column(name = "id", columnDefinition = "INT", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer docId;

    @Column(name = "date", columnDefinition = "DATE", nullable = false)
    private LocalDate docDate;

    @Column(name = "number", columnDefinition = "VARCHAR(10)", nullable = false)
    private String docNumber;

    //@Column(name = "type", columnDefinition = "INT", nullable = false)
    @OneToOne
    private Doc docType;

    public DocData() {
    }

    public DocData(LocalDate docDate, String docNumber, Doc docType) {
        this.docDate = docDate;
        this.docNumber = docNumber;
        this.docType = docType;
    }

    public Integer getDocId() {
        return docId;
    }

    public void setDocId(Integer docId) {
        this.docId = docId;
    }

    public LocalDate getDocDate() {
        return docDate;
    }

    public void setDocDate(LocalDate docDate) {
        this.docDate = docDate;
    }

    public String getDocNumber() {
        return docNumber;
    }

    public void setDocNumber(String docNumber) {
        this.docNumber = docNumber;
    }

    public Doc getDocType() {
        return docType;
    }

    public void setDocType(Doc docType) {
        this.docType = docType;
    }
}

