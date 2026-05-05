package com.example.service.exception;

import com.example.transaction.rest.model.RestErrorCode;
import com.example.transaction.rest.model.RestErrorResponse;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class ErrorResponseFactory {

    public RestErrorResponse buildRestErrorResponse(int status, ErrorCode code, String message) {
        return new RestErrorResponse(
                status,
                RestErrorCode.valueOf(code.name()),
                message,
                Instant.now().toEpochMilli()
        );
    }
}