package com.example.service.service;

import com.example.service.messaging.EventPublisher;
import com.example.service.model.TransactionEntity;
import com.example.service.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository repository;
    private final EventPublisher<TransactionEntity> publisher;

    @Override
    public TransactionEntity createTransaction(TransactionEntity transactionEntity) {

        TransactionEntity saved = repository.save(transactionEntity);
        log.info("Transaction saved: {}", saved.getId());

        publisher.publish(saved);

        return saved;
    }
}