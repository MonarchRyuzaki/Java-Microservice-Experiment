package com.shiryu21.alertservice;

import com.shiryu21.alertservice.entity.CryptoAlert;
import com.shiryu21.alertservice.entity.User;
import com.shiryu21.alertservice.repository.AlertRepository;
import com.shiryu21.alertservice.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class AlertServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AlertServiceApplication.class, args);
    }
    @Bean
    public CommandLineRunner testDatabase(AlertRepository alertRepo, UserRepository userRepo) {
        return args -> {
            // Create a User
            User user = User.builder()
                    .username("shiryu21")
                    .email("gangulyshivam6@gmail.com")
                    .build();

            // Create an Alert
            CryptoAlert alert = CryptoAlert.builder()
                    .symbol("BTC")
                    .targetPrice(100000.00)
                    .condition("GREATER_THAN")
                    .user(user)
                    .build();

            user.setAlerts(List.of(alert));

            // Save (Cascade will save the Alert too)
            userRepo.save(user);

            System.out.println("✅ DATA SAVED! User ID: " + user.getId());
        };
    }
}
