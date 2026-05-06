package com.example.service.factory;

import com.example.service.event.TransactionEvent;
import com.example.service.model.TransactionEntity;
import com.example.service.model.UserEntity;
import com.example.service.model.enums.Currency;
import com.example.service.model.enums.Location;
import com.example.transaction.rest.model.RestTransactionRequest;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TransactionEntityFactory {

    public static TransactionEntity buildTransactionEntity(RestTransactionRequest request, String clientIp) {

        return Optional.ofNullable(request)
                .map(transactionRequest -> TransactionEntity.builder()
                        .id(request.getTransactionId())
                        .amount(BigDecimal.valueOf(request.getAmount()))
                        .currency(Currency.valueOf(request.getCurrency().getValue()))
                        .location(Location.valueOf(request.getLocation().getValue()))
                        .clientIp(clientIp)
                        .timestamp(Instant.ofEpochMilli(request.getTimestamp()))
                        .user(UserEntity.builder().id(request.getUserId()).build())
                        .build())
                .orElse(null);
    }

    public static TransactionEvent buildTransactionEvent(TransactionEntity transactionEntity) {

        return Optional.ofNullable(transactionEntity)
                .map(transaction -> TransactionEvent.builder()
                        .transactionId(transactionEntity.getId())
                        .userId(transactionEntity.getUser().getId())
                        .amount(transactionEntity.getAmount().doubleValue())
                        .currency(transactionEntity.getCurrency().name())
                        .location(transactionEntity.getLocation().name())
                        .clientIp(transactionEntity.getClientIp())
                        .timestamp(transactionEntity.getTimestamp().toEpochMilli())
                        .build())
                .orElse(null);
    }
}