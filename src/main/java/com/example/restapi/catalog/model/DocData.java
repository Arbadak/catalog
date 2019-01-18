package com.example.restapi.catalog.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Version;
import java.time.LocalDate;

/**
 * Вспомогателная сущность для хранения данных о "документ пользователя"
 */
@Entity
@Table(name = "doc_data")
public class DocData {

    @Id
    @Column(name = "id", columnDefinition = "INT")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer docId;

    @Version
    @Column(name = "OPTLOCK")
    private Integer optlock;

    @Column(name = "date", nullable = false)
    private LocalDate docDate;

    @Column(name = "number", length = 10, nullable = false)
    private String docNumber;

    @ManyToOne
    @JoinColumn(name = "doc_Type", nullable = false)
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