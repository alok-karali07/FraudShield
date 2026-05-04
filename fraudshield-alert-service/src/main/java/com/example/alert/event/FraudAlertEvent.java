package com.example.alert.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FraudAlertEvent {

    private String transactionId;
    private String userId;
    private Double amount;
    private String currency;
    private String location;
    private String reason;
    private Long timestamp;
}