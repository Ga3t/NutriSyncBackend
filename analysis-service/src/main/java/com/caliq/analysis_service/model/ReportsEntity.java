package com.caliq.analysis_service.model;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDate;

@Entity
@Table(name="REPORTS")
public class ReportsEntity {

    @Column(name="ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="USER_ID")
    private Long userId;

    @Column(name="DATE")
    private LocalDate date;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name="REPORT", columnDefinition = "json")
    private JsonNode report;

}
