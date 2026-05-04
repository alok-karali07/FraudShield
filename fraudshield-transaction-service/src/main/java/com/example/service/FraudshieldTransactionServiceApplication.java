package com.example.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class FraudshieldTransactionServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(FraudshieldTransactionServiceApplication.class, args);
    }

}
