package com.example.alert.consumer;

import com.example.alert.event.FraudAlertEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class FraudAlertConsumer {

    @KafkaListener(
            topics = "${app.kafka.topic.fraud-detected}",
            groupId = "fraudshield-alert-group"
    )
    public void consumeFraudAlert(FraudAlertEvent alert, Acknowledgment acknowledgment) {

        try {
            log.warn("🚨 FRAUD ALERT RECEIVED");
            log.warn("   Transaction ID : {}", alert.getTransactionId());
            log.warn("   User ID        : {}", alert.getUserId());
            log.warn("   Amount         : {} {}", alert.getAmount(), alert.getCurrency());
            log.warn("   Location       : {}", alert.getLocation());
            log.warn("   Reason         : {}", alert.getReason());

            acknowledgment.acknowledge();

        } catch (Exception e) {
            log.error("❌ Error processing fraud alert", e);
        }
    }
}