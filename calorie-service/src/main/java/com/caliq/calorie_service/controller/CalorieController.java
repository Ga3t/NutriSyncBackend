package com.caliq.calorie_service.controller;


import com.caliq.calorie_service.models.response.MainPageResponse;
import com.caliq.calorie_service.models.response.MealByDateResponse;
import com.caliq.calorie_service.models.types.MealType;
import com.caliq.calorie_service.service.CaloryService;
import com.caliq.core.dto.MealDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/calapp")
public class CalorieController {

    CaloryService caloryService;

    public CalorieController(CaloryService caloryService) {
        this.caloryService = caloryService;
    }

    @PostMapping("/savemeal")
    public ResponseEntity<String> saveMeal (@RequestHeader("X-User-ID") String userId,
                                            @RequestParam("MealType") MealType mealType,
                                            @RequestParam("DateTime")LocalDateTime dateTime,
                                            @RequestBody MealDto mealDto) {
        Long userID = Long.parseLong(userId);
        return ResponseEntity.ok(caloryService.saveMealToDb(userID, mealDto, dateTime,mealType));
    }

    @GetMapping("/showmeal")
    public ResponseEntity<MealByDateResponse> showMeal(@RequestHeader("X-User-ID") String userId,
                                                       @RequestParam("date") LocalDate time){
        Long userID = Long.parseLong(userId);
        return ResponseEntity.ok(caloryService.getMealByDate(userID,time));
    }

    @GetMapping("/mainpage")
    public ResponseEntity<MainPageResponse> showInfoForMainPage(@RequestHeader("X-User-ID") String userId){
        Long userID = Long.parseLong(userId);
        return ResponseEntity.ok(caloryService.showMainPageInfo(userID));
    }

    @PostMapping("/addwater")
    public ResponseEntity<BigDecimal> addWaterController(@RequestHeader("X-User-ID") String userId,
                                                         @RequestParam("Date") LocalDate time,
                                                         @RequestParam("Water-To-Add") BigDecimal water){
        Long userID = Long.parseLong(userId);
        BigDecimal waterResponse = caloryService.addWaterToLogs(userID, water, time);
        return ResponseEntity.ok(waterResponse);
    }
    
}
