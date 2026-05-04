package com.example.service.messaging;

import com.example.service.config.KafkaConfigProps;
import com.example.service.event.TransactionEvent;
import com.example.service.model.TransactionEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import static com.example.service.factory.TransactionEntityFactory.buildTransactionEvent;

@Component
@RequiredArgsConstructor
@Slf4j
public class TransactionKafkaPublisher implements EventPublisher<TransactionEntity> {

    private final KafkaTemplate<String, TransactionEvent> kafkaTemplate;
    private final KafkaConfigProps kafkaConfigProps;

    @Override
    public void publish(TransactionEntity transactionEntity) {

        TransactionEvent event = buildTransactionEvent(transactionEntity);

        kafkaTemplate.send(
                kafkaConfigProps.getTopic().getTransactionsCreated(),
                transactionEntity.getId(),
                event
        );

        log.info("Transaction event published: {}", event);
    }
}