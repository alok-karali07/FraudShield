package com.example.service.exception;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {

    private final ErrorCode errorCode;
    private final int status;

    public BusinessException(ErrorCode errorCode, String message, int status) {
        super(message);
        this.errorCode = errorCode;
        this.status = status;
    }

    public BusinessException(ErrorCode errorCode, int status) {
        super(errorCode.name());
        this.errorCode = errorCode;
        this.status = status;
    }
}