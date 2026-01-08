package com.shiryu21.notificationservice.service;

import com.shiryu21.notificationservice.dto.NotificationEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
public class EmailSender {

    @Bean
    public Consumer<NotificationEvent> sendEmail() {
        return event -> {
            System.out.println("📧 SENDING EMAIL TO: " + event.email());
            System.out.println("   BODY: Your " + event.symbol() + " alert triggered at $" + event.price());
            System.out.println("   -------------------------------------------------");
        };
    }

}
