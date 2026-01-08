package com.shiryu21.alertservice.dto;

public record PriceUpdate(String symbol, Double price, long timestamp) {}