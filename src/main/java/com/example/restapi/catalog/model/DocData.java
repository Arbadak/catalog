package com.example.restapi.catalog.model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "doc_data")
public class DocData {

    @Id
    @Column(name="id", columnDefinition ="INT", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer docId;

    @Column (name="date", columnDefinition = "DATE", nullable = false)
    private LocalDate dateDoc;

    @Column(name="number", columnDefinition ="VARCHAR(10)", nullable = false)
    private String docName;

    @Column(name="type", columnDefinition ="INT",nullable = false)
    private String docType;


}
