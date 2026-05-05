package com.example.service.stream;

import com.example.service.config.KafkaConfigProps;
import com.example.service.event.FraudAlertEvent;
import com.example.service.event.TransactionEvent;
import com.example.service.api.transaction.service.FraudDetector;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafkaStreams;
import org.springframework.kafka.support.serializer.JsonSerde;

@Configuration
@EnableKafkaStreams
@RequiredArgsConstructor
@Slf4j
public class FraudDetectionStream {

    private final FraudDetector fraudDetector;
    private final ObjectMapper objectMapper;
    private final KafkaConfigProps kafkaConfigProps;

    @Bean
    public KStream<String, FraudAlertEvent> process(StreamsBuilder builder) {

        JsonSerde<TransactionEvent> transactionSerde =
                new JsonSerde<>(TransactionEvent.class, objectMapper);

        JsonSerde<FraudAlertEvent> fraudSerde =
                new JsonSerde<>(FraudAlertEvent.class, objectMapper);

        KStream<String, TransactionEvent> stream = builder.stream(
                kafkaConfigProps.getTopic().getTransactionsCreated(),
                Consumed.with(Serdes.String(), transactionSerde)
        );

        KStream<String, FraudAlertEvent> fraudStream = stream
                .filter((key, tx) -> tx != null && fraudDetector.isFraud(tx))
                .mapValues(this::toFraudEvent)
                .peek((key, event) ->
                        log.warn("FRAUD ALERT: {}", event.getTransactionId())
                );

        fraudStream.to(
                kafkaConfigProps.getTopic().getFraudDetected(),
                Produced.with(Serdes.String(), fraudSerde)
        );

        return fraudStream;
    }

    private FraudAlertEvent toFraudEvent(TransactionEvent tx) {

        return FraudAlertEvent.builder()
                .transactionId(tx.getTransactionId())
                .userId(String.valueOf(tx.getUserId()))
                .amount(tx.getAmount())
                .currency(tx.getCurrency())
                .location(tx.getLocation())
                .timestamp(tx.getTimestamp())
                .reason(buildReason(tx))
                .build();
    }

    private String buildReason(TransactionEvent tx) {

        double maxAmount = 10000.0;
        if (tx.getAmount() > maxAmount) {
            return "Amount exceeds threshold";
        }

        if ("OTHER".equalsIgnoreCase(tx.getLocation())) {
            return "Suspicious location";
        }

        return "Normal monitoring trigger";
    }
}