package com.caliq.analysis_service.service;

import com.caliq.analysis_service.model.AnalyseByDateRangeResponse;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public interface AnalyseFoodService {
    AnalyseByDateRangeResponse analyseByDateRange(Long userId, LocalDate startDate, LocalDate endDate);


}
