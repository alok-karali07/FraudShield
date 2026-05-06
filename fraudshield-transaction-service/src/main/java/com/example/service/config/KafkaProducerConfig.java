package com.example.service.config;

import com.example.service.event.TransactionEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.Map;

@Configuration
public class KafkaProducerConfig {

    @Bean
    public ProducerFactory<String, TransactionEvent> producerFactory(
            org.springframework.boot.autoconfigure.kafka.KafkaProperties kafkaProperties) {

        // 1. Start with the properties defined in your application.yaml
        Map<String, Object> config = kafkaProperties.buildProducerProperties(null);

        // 2. Add your custom manual overrides
        // This is safe because key.serializer is now already in the 'config' map from YAML
        config.put(org.springframework.kafka.support.serializer.JsonSerializer.ADD_TYPE_INFO_HEADERS, false);

        return new DefaultKafkaProducerFactory<>(config);
    }

    @Bean
    public KafkaTemplate<String, TransactionEvent> kafkaTemplate(ProducerFactory<String, TransactionEvent> factory) {
        return new KafkaTemplate<>(factory);
    }
}