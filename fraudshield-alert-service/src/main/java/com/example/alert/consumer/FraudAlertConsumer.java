package com.example.alert.consumer;

import com.example.alert.event.FraudAlertEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class FraudAlertConsumer {

    @KafkaListener(
            topics = "${app.kafka.topic.fraud-detected}",
            groupId = "fraudshield-alert-group"
    )
    public void consumeFraudAlert(FraudAlertEvent alert) {
        log.warn("🚨 FRAUD ALERT RECEIVED");
        log.warn("   Transaction ID : {}", alert.getTransactionId());
        log.warn("   User ID        : {}", alert.getUserId());
        log.warn("   Amount         : {} {}", alert.getAmount(), alert.getCurrency());
        log.warn("   Location       : {}", alert.getLocation());
        log.warn("   Reason         : {}", alert.getReason());
        // In future: send email, push notification, update DB etc.
    }
}