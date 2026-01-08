package com.shiryu21.alertservice.dto;

public record NotificationEvent(String email, String symbol, Double price, String condition) {
}
