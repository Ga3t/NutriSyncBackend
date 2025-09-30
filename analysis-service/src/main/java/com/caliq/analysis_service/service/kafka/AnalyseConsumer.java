package com.caliq.analysis_service.service.kafka;

import com.caliq.core.dto.MealDto;
import com.caliq.core.message.MealMessage;
import com.caliq.core.message.ProductMessage;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class AnalyseConsumer {

    AnalyseProducer analyseProducer;

    public AnalyseConsumer(AnalyseProducer analyseProducer) {
        this.analyseProducer = analyseProducer;
    }

    @KafkaListener(topics = "analise-meal_events-topic", groupId = "food-analysis-group",
            containerFactory = "kafkaListenerContainerFactory")
    public void consumeMeal(MealMessage message) {
        MealDto mealDto = message.getMeal();

        List<String> listToOpenFoodFactService = new ArrayList<>();
        List<String> listToFoodSecretService = new ArrayList<>();

        for (MealDto.Dish dish : mealDto.dishes().dish()) {
            String type = dish.type();
            if(Objects.equals(type, "FOODSECRET")){
                listToFoodSecretService.add(dish.code());
            }
            else if(Objects.equals(type, "OPENFOODFACT")){
                listToOpenFoodFactService.add(dish.code());
            }
        }

        if(!listToOpenFoodFactService.isEmpty()){
            ProductMessage productMessage = new ProductMessage(listToOpenFoodFactService,
                message.getUserId(),
                message.getSentAt());
            analyseProducer.sendProductToOpenFoodFactService(productMessage);
        }
        if(!listToFoodSecretService.isEmpty()){
            ProductMessage productMessage = new ProductMessage(listToFoodSecretService,
                    message.getUserId(),
                    message.getSentAt());
            analyseProducer.sendProductToFoodSecretService(productMessage);
        }
    }
}