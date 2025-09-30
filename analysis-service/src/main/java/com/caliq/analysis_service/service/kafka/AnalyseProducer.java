package com.caliq.analysis_service.service.kafka;


import com.caliq.core.message.ProductMessage;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnalyseProducer {

    private final KafkaTemplate<String, ProductMessage> kafkaTemplate;

    public AnalyseProducer(KafkaTemplate<String, ProductMessage> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendProductToFoodSecretService(ProductMessage productMessage){
        kafkaTemplate.send("analise-product-food-secret_events-topic", productMessage.getUserId().toString() , productMessage);
    }

    public void sendProductToOpenFoodFactService(ProductMessage productMessage){
        kafkaTemplate.send("analise-product-open-food-fact_events-topic", productMessage.getUserId().toString() , productMessage);
    }
}
