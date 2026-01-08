package com.shiryu21.marketdataservice.dto;

public record PriceUpdate (String symbol, Double price, long timestamp) {}
