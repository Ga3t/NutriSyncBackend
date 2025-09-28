package com.caliq.analysis_service.model;

import java.math.BigDecimal;

public record Report(
        StandardValuesForUser standardForUser,
        RealUserValues realValues
){
    private record StandardValuesForUser(
            BigDecimal standardKcal,
            BigDecimal standardFiber,
            BigDecimal standardSugar,
            BigDecimal standardCholesterol,
            BigDecimal standardWater
    ){}
    private record RealUserValues(
            BigDecimal userKcal,
            BigDecimal userFiber,
            BigDecimal userSugar,
            BigDecimal userCholesterol,
            BigDecimal userWater
    ){}
}
