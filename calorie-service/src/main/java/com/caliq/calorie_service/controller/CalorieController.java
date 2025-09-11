package com.caliq.calorie_service.controller;


import com.caliq.calorie_service.models.dto.MealDto;
import com.caliq.calorie_service.models.response.MainPageResponse;
import com.caliq.calorie_service.models.response.MealByDateResponse;
import com.caliq.calorie_service.models.types.MealType;
import com.caliq.calorie_service.service.CaloryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/calapp")
public class CalorieController {

    CaloryService caloryService;

    public CalorieController(CaloryService caloryService) {
        this.caloryService = caloryService;
    }

    @PostMapping("/savemeal")
    public ResponseEntity<String> saveMeal (@RequestParam("X-User-ID") Long userId,
                                            @RequestParam("MealType") MealType mealType,
                                            @RequestParam("DateTime")LocalDateTime dateTime,
                                            @RequestBody MealDto mealDto) {
        return ResponseEntity.ok(caloryService.saveMealToDb(userId, mealDto, dateTime,mealType));
    }

    @GetMapping("/showmeal")
    public ResponseEntity<MealByDateResponse> showMeal(@RequestParam("X-User-ID") Long userId,
                                                       @RequestParam("date") LocalDateTime time){
        return null;
    }

    @GetMapping("/mainpage")
    public ResponseEntity<MainPageResponse> showInfoForMainPage(@RequestParam("X-User-ID") Long userId){
        return ResponseEntity.ok(caloryService.showMainPageInfo(userId));
    }
    
}
