package com.caliq.calorie_service.models.entity;


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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", nullable = false)
    private UserModel user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

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
