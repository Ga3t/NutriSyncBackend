package com.caliq.calorie_service.service;

import com.caliq.core.message.MealMessage;
import com.caliq.core.message.WaterMessage;
import com.caliq.core.message.WeightMessage;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class MealProducer {
    private final KafkaTemplate<String, MealMessage> kafkaTemplate;

    public MealProducer(KafkaTemplate<String, MealMessage> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMeal(MealMessage message) {
        kafkaTemplate.send("analise-meal_events-topic", message.getUserId().toString(), message);
    }

//    public void sendWater(WaterMessage message) {
//        kafkaTemplate.send("add-water_events-topic", message.getUserId().toString(), message);
//    }
//
//    public void sendWeight(WeightMessage message) {
//        kafkaTemplate.send("analise-meal_events-topic", message.getUserId().toString(), message);
//    }

}
