package com.example.service.api.transaction.service;

import com.example.service.event.TransactionEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class FraudDetector {

    @Value("${app.fraud.max-amount}")
    private double maxAmount;

    public boolean isFraud(TransactionEvent transactionEvent) {
        return transactionEvent.getAmount() > maxAmount ||
                "OTHER".equalsIgnoreCase(transactionEvent.getLocation());
    }
}