package com.example.restapi.catalog.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

/** Вспомогательная сущность справочник для данных "тип документа пользователя"
 *
 */


@Entity
@Table(name = "doc")
public class Doc {

    @Id
    @Column(name = "id", columnDefinition = "INT", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer docId;


    @Column(name = "name", columnDefinition = "VARCHAR(115)", nullable = false)
    private String docName;

    @Column(name = "code", columnDefinition = "VARCHAR(3)", nullable = false)
    private String docCode;

    public Doc() {
    }

    public Doc(String docName, String docCode) {
        this.docName = docName;
        this.docCode = docCode;
    }

    public Integer getDocId() {
        return docId;
    }

    public void setDocId(Integer docId) {
        this.docId = docId;
    }

    public String getDocName() {
        return docName;
    }

    public void setDocName(String docName) {
        this.docName = docName;
    }

    public String getDocCode() {
        return docCode;
    }

    public void setDocCode(String docCode) {
        this.docCode = docCode;
    }
}
