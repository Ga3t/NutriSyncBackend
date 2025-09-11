package com.caliq.user_service.components;


import com.caliq.core.events.GetPublicKeyRequestEvent;
import com.caliq.core.events.GetPublicKeyResponseEvent;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@Component
public class PublicKeyRequestListener {


    @Value("classpath:keys/key.pub")
    private Resource publicKeyResource;

    private final KafkaTemplate<String, GetPublicKeyResponseEvent> kafkaTemplate;

    public PublicKeyRequestListener(KafkaTemplate<String, GetPublicKeyResponseEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @KafkaListener(topics = "get-public-key-request", groupId = "key-service")
    public void handlePublicKeyRequest(GetPublicKeyRequestEvent event) {
        try (InputStream in = getClass().getClassLoader().getResourceAsStream("keys/key.pub")) {
            String key = new String(in.readAllBytes(), StandardCharsets.UTF_8);
            GetPublicKeyResponseEvent response = new GetPublicKeyResponseEvent(event.getRequestId(), key);
            kafkaTemplate.send("get-public-key-response", response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
