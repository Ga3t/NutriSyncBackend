package com.caliq.api_conection_service.service.impl;

import com.caliq.api_conection_service.exception.ProductNotFoundException;
import com.caliq.api_conection_service.model.AddFoodDto;
import com.caliq.api_conection_service.model.FoodDataResponse;
import com.caliq.api_conection_service.model.FoodInfoDto;
import com.caliq.api_conection_service.service.OpenFoodFactService;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;


@Service
@Primary
public class OpenFoodFactServiceImpl implements OpenFoodFactService {

    private final RestClient restClient;

    public OpenFoodFactServiceImpl(RestClient restClient) {
        this.restClient = restClient;
    }

    public FoodDataResponse getFoodInfo(String barcode) {
        String uri = "https://world.openfoodfacts.org/api/v2/product/{barcode}" +
                "?fields=product_name,nutriscore_data,nutriments,allergens_tags";


        FoodInfoDto dto = restClient.get()
                .uri(uri, barcode)
                .retrieve()
                .body(FoodInfoDto.class);

        if (dto == null || dto.getProduct() == null) {
            throw new ProductNotFoundException("Product not found in database please add it manually or try again");
        }

        FoodInfoDto.Product product = dto.getProduct();

        return new FoodDataResponse(
                dto.getCode(),
                product.getProductName(),
                product.getNutriments() != null ? product.getNutriments().getFat_100g() : null,
                product.getNutriments() != null ? product.getNutriments().getProteins_100g() : null,
                product.getNutriments() != null ? product.getNutriments().getCarbohydrates_100g() : null,
                product.getNutriscoreData() != null ? product.getNutriscoreData().getGrade() : null,
                product.getAllergens()
        );
    }

    @Override
    public String addFoodByBarcode(AddFoodDto addFoodDto) {
        return "";
    }
}
