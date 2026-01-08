package com.shiryu21.marketdataservice.service;

import com.shiryu21.marketdataservice.dto.PriceUpdate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Supplier;

@Configuration
public class PricePublisher {

    // Spring Cloud Stream polls this "Supplier" every second by default
    // It takes the return value and pushes it to Kafka
    @Bean
    public Supplier<PriceUpdate> supplyPrice() {
        return () -> {
            boolean isBtc = Math.random() > 0.5;
            String symbol = isBtc ? "BTC" : "ETH";
            double price = isBtc ? 500000.0 : 3000.0;

            System.out.println("📤 Pushing event: " + symbol);
            return new PriceUpdate(symbol, price, System.currentTimeMillis());
        };
    }
}
