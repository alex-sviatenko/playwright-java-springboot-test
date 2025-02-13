package com.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ProductConditions {
    NEW("New"),
    UNWORN("Unworn"),
    FRESH_ARRIVAL("Fresh Arrival"),
    SEALED("Sealed");

    private final String condition;
}
