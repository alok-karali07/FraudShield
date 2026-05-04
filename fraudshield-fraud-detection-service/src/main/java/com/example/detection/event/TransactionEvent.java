package com.example.detection.event;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionEvent {

    private String transactionId;
    private Long userId;
    private Double amount;
    private String currency;
    private String location;
    private Long timestamp;
}