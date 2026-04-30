package com.example.service.controller;


import com.example.transaction.rest.api.DefaultApi;
import com.example.transaction.rest.model.RestTransactionRequest;
import com.example.transaction.rest.model.RestTransactionResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DefaultController implements DefaultApi {

    @Override
    public ResponseEntity<RestTransactionResponse> createTransaction(RestTransactionRequest restTransactionRequest) {
        return null;
    }
}