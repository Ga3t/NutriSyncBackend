package com.caliq.FoodSecretApiConnection.service.kafka;


import com.caliq.core.message.InfoAboutProductMessage;
import com.caliq.core.response.FoodResponse;
import com.caliq.FoodSecretApiConnection.service.FoodSecretApiService;
import com.caliq.core.message.ProductMessage;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FoodSecretConsumer {

    FoodSecretApiService foodSecretApiService;
    FoodSecretProducer foodSecretProducer;

    public FoodSecretConsumer(FoodSecretApiService foodSecretApiService, FoodSecretProducer foodSecretProducer) {
        this.foodSecretApiService = foodSecretApiService;
        this.foodSecretProducer = foodSecretProducer;
    }

    @KafkaListener(topics = "analise-product-food-secret_events-topic", groupId = "food-analysis-group",
            containerFactory ="kafkaListenerContainerFactory")
    public void consumeProduct(ProductMessage message){

        List<FoodResponse> infoAboutProducts = new ArrayList<>();
        for(String code : message.getCode()){
            FoodResponse foodResponse = foodSecretApiService.getFoodResponse(code);
            infoAboutProducts.add(foodResponse);
        }

        InfoAboutProductMessage infoAboutProductMessage = new InfoAboutProductMessage(infoAboutProducts,
                message.getUserId(), message.getSendAt());
        foodSecretProducer.sendInfoAboutProduct(infoAboutProductMessage);

    }
}
