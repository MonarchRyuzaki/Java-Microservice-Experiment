package com.shiryu21.notificationservice.dto;

public record NotificationEvent(String email, String symbol, Double price, String condition) {
}
