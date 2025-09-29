package com.caliq.calorie_service.models.response;

import com.caliq.core.dto.MealDto;

import java.util.List;

public class MealByDateResponse {

    private String date;
    private List<MealDto> meals;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<MealDto> getMeals() {
        return meals;
    }

    public void setMeals(List<MealDto> meals) {
        this.meals = meals;
    }
}
