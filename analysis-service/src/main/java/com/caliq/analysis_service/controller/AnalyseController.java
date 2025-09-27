package com.caliq.analysis_service.controller;


import com.caliq.analysis_service.model.AnalyseByDateRangeResponse;
import com.caliq.analysis_service.service.AnalyseFoodService;
import jakarta.websocket.server.PathParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("analyse")
public class AnalyseController {

    private AnalyseFoodService analyseFoodService;


    @GetMapping("/{startdate}/{enddate}")
    private ResponseEntity<AnalyseByDateRangeResponse> analyseFood(@PathParam("startDate")LocalDate startDate,
                                                                   @PathParam("enddate") LocalDate endDate,
                                                                   @RequestParam("X-User-ID") Long userId){
        return ResponseEntity.ok(analyseFoodService.analyseByDateRange(userId, startDate,endDate));
    }

}
