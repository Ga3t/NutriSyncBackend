package com.caliq.FoodSecretApiConnection.service;


import com.caliq.FoodSecretApiConnection.models.FoodResponse;
import com.caliq.FoodSecretApiConnection.models.FoodSearchResponse;
import com.caliq.FoodSecretApiConnection.models.RequestDto;
import org.springframework.stereotype.Service;

@Service
public interface FoodSecretApiService {

    FoodSearchResponse getFoodSearchResponse(String search_expression, int pageNo);
    FoodResponse getFoodResponse(String foodId);

}
