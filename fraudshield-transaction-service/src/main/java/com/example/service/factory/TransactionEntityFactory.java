package com.example.service.factory;

import com.example.service.model.enums.Currency;
import com.example.service.model.enums.Location;
import com.example.service.event.TransactionEvent;
import com.example.service.model.TransactionEntity;
import com.example.transaction.rest.model.RestTransactionRequest;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TransactionEntityFactory {

    public static TransactionEntity buildTransactionEntity(RestTransactionRequest request) {
        return TransactionEntity.builder()
                .id(request.getTransactionId())
                .amount(BigDecimal.valueOf(request.getAmount()))
                .currency(Currency.valueOf(request.getCurrency().getValue()))
                .location(Location.valueOf(request.getLocation().getValue()))
                .timestamp(Instant.ofEpochMilli(request.getTimestamp()))
                .build();
    }

    public static TransactionEvent buildTransactionEvent(TransactionEntity transactionEntity) {

        return TransactionEvent.builder()
                .transactionId(transactionEntity.getId())
                .userId(transactionEntity.getUser().getId())
                .amount(transactionEntity.getAmount().doubleValue())
                .currency(transactionEntity.getCurrency().name())
                .location(transactionEntity.getLocation().name())
                .timestamp(transactionEntity.getTimestamp().toEpochMilli())
                .build();
    }
}