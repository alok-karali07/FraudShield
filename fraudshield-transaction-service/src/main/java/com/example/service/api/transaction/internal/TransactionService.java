package com.example.service.api.transaction.internal;

import com.example.service.model.TransactionEntity;

public interface TransactionService {

    TransactionEntity createTransaction(TransactionEntity transactionEntity);
}