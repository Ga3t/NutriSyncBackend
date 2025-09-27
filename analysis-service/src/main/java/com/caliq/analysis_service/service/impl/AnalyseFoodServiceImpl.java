package com.caliq.analysis_service.service.impl;

import com.caliq.analysis_service.model.AnalyseByDateRangeResponse;
import com.caliq.analysis_service.repository.FoodAnalyseRepository;
import com.caliq.analysis_service.service.AnalyseFoodService;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@Primary
public class AnalyseFoodServiceImpl implements AnalyseFoodService{

    private FoodAnalyseRepository foodAnalyseRepository;

    public AnalyseFoodServiceImpl(FoodAnalyseRepository foodAnalyseRepository) {
        this.foodAnalyseRepository = foodAnalyseRepository;
    }

    @Override
    public AnalyseByDateRangeResponse analyseByDateRange(Long userId, LocalDate startDate, LocalDate endDate) {
        return null;
    }
}
