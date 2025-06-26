package com.breaditnow.common.domain;

public enum ProductStatus {
    FOR_SALE,
    HIDDEN,
    SOLD_OUT;

    public static ProductStatus from(String status) {
        if (status == null || status.isBlank()) {
            return null;
        }
        try {
            return ProductStatus.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
