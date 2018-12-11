package com.example.restapi.catalog.model;

import javax.persistence.*;

@Entity
@Table(name = "doc")
public class Doc {

    @Id
    @Column(name="id", columnDefinition ="INT", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer docId;


    @Column(name="name", columnDefinition ="VARCHAR(115)", nullable = false)
    private String docName;

    @Column(name="code", columnDefinition ="VARCHAR(3)",nullable = false)
    private String docCode;

    public Doc() {
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
