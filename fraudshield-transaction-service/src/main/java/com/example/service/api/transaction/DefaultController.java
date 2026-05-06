package com.example.service.api.transaction;

import com.example.service.api.transaction.internal.TransactionService;
import com.example.service.factory.TransactionEntityFactory;
import com.example.service.model.TransactionEntity;
import com.example.transaction.rest.api.TransactionApi;
import com.example.transaction.rest.model.RestTransactionRequest;
import com.example.transaction.rest.model.RestTransactionResponse;
import com.example.transaction.rest.model.RestTransactionStatus;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@RestController
@RequiredArgsConstructor
public class DefaultController implements TransactionApi {

    private final TransactionService transactionService;

    @Override
    public ResponseEntity<RestTransactionResponse> createTransaction(RestTransactionRequest request) {
        String clientIp = getClientIp();

        TransactionEntity saved = transactionService.createTransaction(
                TransactionEntityFactory.buildTransactionEntity(request, clientIp)
        );

        RestTransactionResponse response = new RestTransactionResponse()
                .transactionId(saved.getId())
                .status(RestTransactionStatus.ACCEPTED)
                .message("Transaction accepted for fraud processing");

        return ResponseEntity.accepted().body(response);
    }

    private String getClientIp() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

        String xForwardedFor = request.getHeader("X-Forwarded-For");

        if (xForwardedFor != null && !xForwardedFor.isEmpty()) {
            return xForwardedFor.split(",")[0].trim();
        }

        return request.getRemoteAddr();
    }
}