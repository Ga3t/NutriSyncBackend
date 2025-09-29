package com.caliq.analysis_service.model;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDate;

@Entity
@Table(name="REPORTS")
public class ReportsEntity {


    @Id
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


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public JsonNode getReport() {
        return report;
    }

    public void setReport(JsonNode report) {
        this.report = report;
    }
}
