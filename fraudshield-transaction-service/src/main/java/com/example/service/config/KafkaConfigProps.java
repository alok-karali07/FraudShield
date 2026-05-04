package com.example.service.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "app.kafka")
public class KafkaConfigProps {

    private String bootstrapServers;

    private Topic topic;

    @Data
    public static class Topic {
        private String transactionsCreated;
        private String fraudDetected;
    }
}