package com.example.service.messaging;

import com.example.service.event.TransactionEvent;
import com.example.service.model.TransactionEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import static com.example.service.factory.TransactionEntityFactory.buildTransactionEvent;

@Component
@RequiredArgsConstructor
@Slf4j
public class TransactionKafkaPublisher implements EventPublisher<TransactionEntity> {

    private final KafkaTemplate<String, TransactionEvent> kafkaTemplate;

    @Value("${app.kafka.topic.transactions-created}")
    private String topic;

    @Override
    public void publish(TransactionEntity entity) {

        TransactionEvent event = buildTransactionEvent(entity);

        kafkaTemplate.send(topic, entity.getId(), event);
        log.info("Transaction event published: {}", entity.getId());
    }
}