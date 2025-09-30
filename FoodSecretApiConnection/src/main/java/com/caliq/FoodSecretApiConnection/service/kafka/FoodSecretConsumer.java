package com.caliq.FoodSecretApiConnection.service.kafka;


import com.caliq.core.response.FoodResponse;
import com.caliq.FoodSecretApiConnection.service.FoodSecretApiService;
import com.caliq.core.message.ProductMessage;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class FoodSecretConsumer {

    FoodSecretApiService foodSecretApiService;

    public FoodSecretConsumer(FoodSecretApiService foodSecretApiService) {
        this.foodSecretApiService = foodSecretApiService;
    }

    @KafkaListener(topics = "analise-product-food-secret_events-topic", groupId = "food-analysis-group",
            containerFactory ="kafkaListenerContainerFactory")
    public void consumeProduct(ProductMessage message){

        for(String code : message.getCode()){
            FoodResponse foodResponse = foodSecretApiService.getFoodResponse(code);
        }
    }
}
