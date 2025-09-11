package com.caliq.api_conection_service.model;


import jdk.jfr.DataAmount;


public class FoodDataResponse {


    private String code;
    private String name;
    private Double fat;
    private Double protein;
    private Double carbohydrates;
    private String nutriScore;
    private String[] allergens;

    public FoodDataResponse(String code, String name, Double fat, Double protein, Double carbohydrates, String nutriScore, String[] allergens) {
        this.code =code;
        this.name = name;
        this.fat = fat;
        this.protein = protein;
        this.carbohydrates = carbohydrates;
        this.nutriScore = nutriScore;
        this.allergens = allergens;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getFat() {
        return fat;
    }

    public void setFat(Double fat) {
        this.fat = fat;
    }

    public Double getProtein() {
        return protein;
    }

    public void setProtein(Double protein) {
        this.protein = protein;
    }

    public Double getCarbohydrates() {
        return carbohydrates;
    }

    public void setCarbohydrates(Double carbohydrates) {
        this.carbohydrates = carbohydrates;
    }

    public String getNutriScore() {
        return nutriScore;
    }

    public void setNutriScore(String nutriScore) {
        this.nutriScore = nutriScore;
    }

    public String[] getAllergens() {
        return allergens;
    }

    public void setAllergens(String[] allergens) {
        this.allergens = allergens;
    }
}
