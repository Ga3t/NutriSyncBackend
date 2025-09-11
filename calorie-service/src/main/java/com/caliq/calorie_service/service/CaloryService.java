package com.caliq.calorie_service.service;


import com.caliq.calorie_service.models.entity.UserModel;
import com.caliq.calorie_service.models.response.MainPageResponse;
import com.caliq.calorie_service.models.response.MealByDateResponse;
import com.caliq.calorie_service.models.dto.MealDto;
import com.caliq.calorie_service.models.types.MealType;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public interface CaloryService{

    String saveMealToDb(Long userId, MealDto mealDto, LocalDateTime time, MealType mealType);
    MealByDateResponse getMealByDate(Long userId, LocalDateTime time);
    MainPageResponse showMainPageInfo(Long userId);
    void saveToCaloryLogs(MealDto mealDto, LocalDateTime time, UserModel user);
}
