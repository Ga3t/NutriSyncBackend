package com.caliq.api_gateway.service;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

@Component
public class KeyRequester {


    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    public KeyRequester(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @PostConstruct
    public void requestKey() {
        kafkaTemplate.send("key-request-topic", "give me key");
    }

    @KafkaListener(topics = "key-response-topic", groupId = "api-gateway-group")
    public void onKeyResponse(String publicKey) throws IOException {
        Files.writeString(Paths.get("key.pub"), publicKey, StandardCharsets.UTF_8);
        System.out.println("Public key saved to key.pub");
    }
}
