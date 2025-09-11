package com.caliq.api_gateway.configs;

//import com.caliq.core.events.GetPublicKeyRequestEvent;
//import org.apache.kafka.clients.admin.NewTopic;
//import org.apache.kafka.clients.consumer.ConsumerConfig;
//import org.apache.kafka.clients.producer.ProducerConfig;
//import org.apache.kafka.common.protocol.types.Field;
//import org.apache.kafka.common.serialization.StringDeserializer;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
//import org.springframework.kafka.config.TopicBuilder;
//import org.springframework.kafka.core.*;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@Configuration
//public class KafkaConfig {
//
//    @Value("${spring.kafka.bootstrap-servers}")
//    private String bootstrapServers;
//
//    @Value("${spring.kafka.producer.key-serializer}")
//    private String keySerializer;
//
//    @Value("${spring.kafka.producer.value-serializer}")
//    private String valueSerializer;
//
//    @Value("${spring.kafka.producer.acks}")
//    private String acks;
//
//    @Value("${spring.kafka.producer.properties.delivery.timeout.ms}")
//    private String deliveryTimeout;
//
//    @Value("${spring.kafka.producer.properties.linger.ms}")
//    private String linger;
//
//    @Value("${spring.kafka.producer.properties.request.timeout.ms}")
//    private String requestTimeout;
//
//    @Value("${spring.kafka.producer.properties.enable.idempotence}")
//    private String idempotence;
//
//    @Value("${spring.kafka.producer.properties.max.in.flight.requests.per.connection}")
//    private String maxInflightRequests;
//
//    @Bean
//    ProducerFactory<String, GetPublicKeyRequestEvent> producerFactory(){
//        return new DefaultKafkaProducerFactory<>(producerConfigs());
//    }
//
//    @Bean
//    ProducerFactory<String, String> producerFactoryString(){
//        return new DefaultKafkaProducerFactory<>(producerConfigs());
//    }
//
//    @Bean
//    KafkaTemplate<String, GetPublicKeyRequestEvent> kafkaTemplate(){
//        return new KafkaTemplate<String, GetPublicKeyRequestEvent>(producerFactory());
//    }
//
//    @Bean
//    KafkaTemplate<String, String> kafkaTemplateString(){
//        return new KafkaTemplate<String, String>(producerFactoryString());
//    }
//
//    @Bean
//    NewTopic createTopic(){
//        return TopicBuilder.name("key-public-events-topic")
//                .partitions(3)
//                .replicas(3)
//                .configs(Map.of("min.insync.replicas", "2"))
//                .build();
//    }
//
//    Map<String,Object> producerConfigs(){
//        Map<String, Object> config = new HashMap<>();
//
//        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
//        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, keySerializer);
//        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, valueSerializer);
//        config.put(ProducerConfig.ACKS_CONFIG, acks);
//        config.put(ProducerConfig.DELIVERY_TIMEOUT_MS_CONFIG, deliveryTimeout);
//        config.put(ProducerConfig.LINGER_MS_CONFIG, linger);
//        config.put(ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG, requestTimeout);
//        config.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, idempotence);
//        config.put(ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION, maxInflightRequests);
//
//        return config;
//    }
//
//    @Bean
//    public ConsumerFactory<String, String> consumerFactory() {
//        Map<String, Object> props = new HashMap<>();
//        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
//        props.put(ConsumerConfig.GROUP_ID_CONFIG, "ledger-service-consumer");
//        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
//        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
//        return new DefaultKafkaConsumerFactory<>(props);
//    }
//
//    @Bean
//    public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory() {
//        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
//        factory.setConsumerFactory(consumerFactory());
//        return factory;
//    }
//}
