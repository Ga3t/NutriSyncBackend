package com.caliq.analysis_service.service;

import com.caliq.core.message.MealMessage;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class MealConsumer {

    @KafkaListener(topics = "analise-meal_events-topic", groupId = "food-analysis-group",
            containerFactory = "kafkaListenerContainerFactory")
    public void consume(MealMessage message) {
        System.out.println("📩 Получено сообщение от Kafka:");
        System.out.println("UserId = " + message.getUserId());
        System.out.println("Meal = " + message.getMeal());
        System.out.println("SentAt = " + message.getSentAt());
    }
}