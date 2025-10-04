package com.caliq.analysis_service.service.kafka;

import com.caliq.analysis_service.model.WeightLogsEntity;
import com.caliq.analysis_service.repository.WeightLogsRepository;
import com.caliq.analysis_service.service.AnalyseFoodService;
import com.caliq.core.dto.MealDto;
import com.caliq.core.message.InfoAboutProductMessage;
import com.caliq.core.message.MealMessage;
import com.caliq.core.message.ProductMessage;
import com.caliq.core.message.WeightMessage;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class AnalyseConsumer {

    private AnalyseProducer analyseProducer;
    private AnalyseFoodService analyseFoodService;
    private WeightLogsRepository weightLogsRepository;

    public AnalyseConsumer(AnalyseProducer analyseProducer, AnalyseFoodService analyseFoodService, WeightLogsRepository weightLogsRepository) {
        this.analyseProducer = analyseProducer;
        this.analyseFoodService = analyseFoodService;
        this.weightLogsRepository = weightLogsRepository;
    }

    @KafkaListener(topics = "analise-meal_events-topic", groupId = "food-analysis-group",
            containerFactory = "kafkaListenerContainerFactory")
    public void consumeMeal(MealMessage message) {
        MealDto mealDto = message.getMeal();
        analyseFoodService.createInitReport(mealDto, message.getUserId(), message.getSentAt());
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

    @KafkaListener(topics = "product-food-response_events-topic", groupId = "food-analysis-group",
            containerFactory = "kafkaListenerContainerFactory")
    public void getInfoAboutProducts(InfoAboutProductMessage message){
    }

    @KafkaListener(topics = "new-weight_event_topic", groupId = "food-analysis-group",
            containerFactory = "kafkaListenerContainerFactory")
    @Transactional
    public void consumeNewWeight(WeightMessage message) {
        WeightLogsEntity weightLogs = weightLogsRepository
                .findByUserIdAndWeighingDate(message.getUserId(), message.getSendAt())
                .map(existing -> {
                    existing.setWeight(message.getWeight());
                    return existing;
                })
                .orElseGet(() -> new WeightLogsEntity(
                        message.getWeight(),
                        message.getSendAt(),
                        message.getUserId()
                ));
        weightLogsRepository.save(weightLogs);
    }
}