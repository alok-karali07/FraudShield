package com.example.service.api.transaction.service;

import com.example.service.exception.ErrorCode;
import com.example.service.exception.NotFoundException;
import com.example.service.messaging.EventPublisher;
import com.example.service.model.TransactionEntity;
import com.example.service.repository.TransactionRepository;
import com.example.service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;
    private final EventPublisher<TransactionEntity> publisher;

    @Override
    public TransactionEntity createTransaction(TransactionEntity transactionEntity) {

        Long userId = transactionEntity.getUser().getId();

        if (!userRepository.existsById(userId)) {
            throw new NotFoundException(ErrorCode.NOT_FOUND, "User not found with id: " + userId);
        }

        TransactionEntity saved = transactionRepository.save(transactionEntity);
        log.info("Transaction saved: id={}, userId={}", saved.getId(), userId);

        publisher.publish(saved);

        return saved;
    }
}