package com.caliq.analysis_service.service;

import com.caliq.analysis_service.model.AnalyseByDateRangeResponse;
import com.caliq.core.dto.MealDto;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public interface AnalyseFoodService {
    AnalyseByDateRangeResponse analyseByDateRange(Long userId, LocalDate startDate, LocalDate endDate);
    void takeDesInOpenFoodFactService(String code, Long UserId, LocalDateTime sendAt);
    void takeDesInFoodSecretService (String code, Long UserId, LocalDateTime sendAt);
    void createInitReport(MealDto mealDto, Long userId, LocalDateTime sendAt);
}
