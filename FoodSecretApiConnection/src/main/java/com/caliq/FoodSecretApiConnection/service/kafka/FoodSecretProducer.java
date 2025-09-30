package com.caliq.FoodSecretApiConnection.service.kafka;


import com.caliq.core.message.InfoAboutProductMessage;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class FoodSecretProducer {

    private final KafkaTemplate<String, InfoAboutProductMessage> kafkaTemplate;

    public FoodSecretProducer(KafkaTemplate<String, InfoAboutProductMessage> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendInfoAboutProduct(InfoAboutProductMessage message){
        kafkaTemplate.send("product-food-secret-response_events-topic", message.getUserId().toString() , message);
    }

}
