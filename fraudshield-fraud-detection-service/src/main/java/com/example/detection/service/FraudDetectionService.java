package com.example.detection.service;

import com.example.detection.event.FraudAlertEvent;
import com.example.detection.event.TransactionEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafkaStreams;
import org.springframework.kafka.core.KafkaTemplate;

@Configuration
@EnableKafkaStreams
@RequiredArgsConstructor
@Slf4j
public class FraudDetectionService {

    private final KafkaTemplate<String, FraudAlertEvent> kafkaTemplate;
    private final ObjectMapper objectMapper;

    @Value("${app.kafka.topic.transactions-created}")
    private String inputTopic;

    @Value("${app.kafka.topic.fraud-detected}")
    private String outputTopic;

    @Value("${app.fraud.max-amount}")
    private double maxAmount;

    @Bean
    public KStream<String, String> kafkaStream(StreamsBuilder builder) {

        KStream<String, String> stream = builder.stream(inputTopic);

        stream.foreach((transactionId, value) -> {
            try {
                TransactionEvent event = objectMapper.readValue(value, TransactionEvent.class);

                String reason = evaluateFraud(event);

                if (reason != null) {
                    log.warn("FRAUD DETECTED: transactionId={}, reason={}", transactionId, reason);
                    publishFraudAlert(event, reason);
                } else {
                    log.info("Transaction CLEARED: {}", transactionId);
                }

            } catch (Exception e) {
                log.error("Error processing transaction: {}", transactionId, e);
            }
        });

        return stream;
    }

    private String evaluateFraud(TransactionEvent event) {

        if (event.getAmount() > maxAmount) {
            return "Amount exceeds threshold of " + maxAmount;
        }

        if ("OTHER".equalsIgnoreCase(event.getLocation())) {
            return "Suspicious location";
        }

        return null;
    }

    private void publishFraudAlert(TransactionEvent event, String reason) {

        FraudAlertEvent alert = FraudAlertEvent.builder()
                .transactionId(event.getTransactionId())
                .userId(event.getUserId())
                .amount(event.getAmount())
                .currency(event.getCurrency())
                .location(event.getLocation())
                .reason(reason)
                .timestamp(event.getTimestamp())
                .build();

        kafkaTemplate.send(outputTopic, event.getTransactionId(), alert);
    }
}