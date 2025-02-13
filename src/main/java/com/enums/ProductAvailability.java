package com.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ProductAvailability {
    AVAILABLE_NOW("Available Now"),
    LIMITED_STOCK("Limited Stock"),
    PRE_ORDER("Pre-order"),
    IN_STOCK("In Stock");

    private final String availability;
}
