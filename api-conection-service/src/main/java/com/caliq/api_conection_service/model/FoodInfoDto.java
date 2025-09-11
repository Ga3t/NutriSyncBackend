package com.caliq.api_conection_service.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FoodInfoDto {


    @JsonProperty("code")
    private String code;

    @JsonProperty("product")
    private Product product;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Product {

        @JsonProperty("product_name")
        private String productName;

        private Nutriments nutriments;

        @JsonProperty("nutriscore_data")
        private NutriscoreData nutriscoreData;

        @JsonProperty("allergens_tags")
        private String[] allergens;

        public String getProductName() { return productName; }
        public void setProductName(String productName) { this.productName = productName; }

        public Nutriments getNutriments() { return nutriments; }
        public void setNutriments(Nutriments nutriments) { this.nutriments = nutriments; }

        public NutriscoreData getNutriscoreData() { return nutriscoreData; }
        public void setNutriscoreData(NutriscoreData nutriscoreData) { this.nutriscoreData = nutriscoreData; }

        public String[] getAllergens() { return allergens; }
        public void setAllergens(String[] allergens) { this.allergens = allergens; 
            
            
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Nutriments {
        private Double fat_100g;
        private Double proteins_100g;
        private Double carbohydrates_100g;

        public Double getFat_100g() { return fat_100g; }
        public void setFat_100g(Double fat_100g) { this.fat_100g = fat_100g; }

        public Double getProteins_100g() { return proteins_100g; }
        public void setProteins_100g(Double proteins_100g) { this.proteins_100g = proteins_100g; }

        public Double getCarbohydrates_100g() { return carbohydrates_100g; }
        public void setCarbohydrates_100g(Double carbohydrates_100g) { this.carbohydrates_100g = carbohydrates_100g; }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class NutriscoreData {
        private String grade;

        public String getGrade() { return grade; }
        public void setGrade(String grade) { this.grade = grade; }
    }

    public boolean isEmpty(){

        if(product.getProductName().isEmpty() && code.isEmpty())
            return true;
        else
            return false;
    }

}
