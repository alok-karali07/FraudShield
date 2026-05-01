package com.example.service.enums;

public enum ErrorMessage {

    INVALID_TRANSACTION("Invalid transaction request"),
    DUPLICATE_TRANSACTION("Duplicate transaction detected"),
    FRAUD_DETECTED("Transaction flagged as fraud"),
    SYSTEM_ERROR("Internal server error");

    private final String message;

    ErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}