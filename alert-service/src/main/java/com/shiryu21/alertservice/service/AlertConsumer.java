package com.shiryu21.alertservice.service;

import com.shiryu21.alertservice.dto.NotificationEvent;
import com.shiryu21.alertservice.dto.PriceUpdate;
import com.shiryu21.alertservice.entity.CryptoAlert;
import com.shiryu21.alertservice.repository.AlertRepository;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.function.Consumer;

@Configuration
public class AlertConsumer {

    private final AlertRepository alertRepository;
    private final StreamBridge streamBridge;

    public AlertConsumer(AlertRepository alertRepository, StreamBridge streamBridge) {
        this.alertRepository = alertRepository;
        this.streamBridge = streamBridge;
    }

    @Bean
    public Consumer<PriceUpdate> checkAlerts() {
        return priceUpdate -> {
            System.out.println("📥 RECEIVED: " + priceUpdate.symbol() + " at $" + priceUpdate.price());

            // 1. Find all alerts for this coin (e.g., all BTC alerts)
            List<CryptoAlert> alerts = alertRepository.findBySymbol(priceUpdate.symbol());

            // 2. Check conditions
            alerts.forEach(alert -> {
                if (isConditionMet(alert, priceUpdate.price())) {
                    System.out.println("🚨 ALERT TRIGGERED! User: " + alert.getUser().getEmail() +
                            " | Target: " + alert.getTargetPrice());
                    // Later: Send to Notification Service

                    NotificationEvent event = new NotificationEvent(alert.getUser().getEmail(), alert.getSymbol(), priceUpdate.price(), alert.getCondition());

                    streamBridge.send("notification-topic", event);
                    System.out.println("🚨 ALERT SENT TO KAFKA for: " + alert.getUser().getEmail());
                }
            });
        };
    }

    private boolean isConditionMet(CryptoAlert alert, Double currentPrice) {
        if ("GREATER_THAN".equals(alert.getCondition()) && currentPrice > alert.getTargetPrice()) {
            return true;
        }
        if ("LESS_THAN".equals(alert.getCondition()) && currentPrice < alert.getTargetPrice()) {
            return true;
        }
        return false;
    }
}
