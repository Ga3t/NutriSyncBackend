package com.caliq.analysis_service.model;

import java.math.BigDecimal;

public record Report(
        BigDecimal standardKcal,
        BigDecimal userKcal,
        BigDecimal standardFiber,
        BigDecimal userFiber,
        BigDecimal standardSugar,
        BigDecimal userSugar,
        BigDecimal standardCholesterol,
        BigDecimal userCholesterol
){}
