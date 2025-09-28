package com.caliq.analysis_service.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name="WEIGHT_LOGS")
public class WeightLogsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID")
    private Long id;

    @Column(name="WEIGHT", nullable = false)
    private BigDecimal weight;

    @Column(name="WEIGHT_DATE", nullable = false)
    private LocalDate weighingDate;

    @Column(name="USER_ID")
    private Long userId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {return userId;}

    public void setUserId(Long userId) {this.userId = userId;}

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public LocalDate getWeighingDate() {
        return weighingDate;
    }

    public void setWeighingDate(LocalDate weighingDate) {
        this.weighingDate = weighingDate;
    }
}