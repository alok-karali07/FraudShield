package com.example.service.controller;

import com.example.service.factory.TransactionEntityFactory;
import com.example.service.model.TransactionEntity;
import com.example.service.service.TransactionService;
import com.example.transaction.rest.api.DefaultApi;
import com.example.transaction.rest.model.RestTransactionRequest;
import com.example.transaction.rest.model.RestTransactionResponse;
import com.example.transaction.rest.model.RestTransactionStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DefaultController implements DefaultApi {

    private final TransactionService transactionService;

    @Override
    public ResponseEntity<RestTransactionResponse> createTransaction(RestTransactionRequest request) {

        TransactionEntity saved = transactionService.createTransaction(
                TransactionEntityFactory.buildTransactionEntity(request)
        );

        RestTransactionResponse response = new RestTransactionResponse()
                .transactionId(saved.getId())
                .status(RestTransactionStatus.ACCEPTED)
                .message("Transaction accepted for fraud processing");

        return ResponseEntity.accepted().body(response);
    }
}