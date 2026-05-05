package com.example.service.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {

    NOT_FOUND,
    INVALID_REQUEST,
    DUPLICATE_TRANSACTION,
    VALIDATION_FAILED,
    SYSTEM_ERROR,
    UNAUTHORIZED,
    SERVICE_UNAVAILABLE
}