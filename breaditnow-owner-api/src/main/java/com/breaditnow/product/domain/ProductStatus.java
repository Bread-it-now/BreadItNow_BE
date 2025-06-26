package com.breaditnow.product.domain;

import com.breaditnow.common.exception.OwnerException;

import java.util.stream.Stream;

import static com.breaditnow.common.exception.OwnerErrorCode.INVALID_PRODUCT_STATUS;

public enum ProductStatus {
    FOR_SALE, // 판매 중
    HIDDEN,   // 숨김
    SOLD_OUT;  // 품절

    public static ProductStatus from(String value) {
        if (value == null) {
            return null;
        }
        return Stream.of(ProductStatus.values())
                .filter(status -> status.name().equalsIgnoreCase(value))
                .findFirst()
                .orElseThrow(() -> new OwnerException(INVALID_PRODUCT_STATUS));
    }
}
