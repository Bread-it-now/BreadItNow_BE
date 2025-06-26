package com.breaditnow.product.domain;

import com.breaditnow.common.exception.OwnerException;

import java.util.stream.Stream;

import static com.breaditnow.common.exception.OwnerErrorCode.INVALID_PRODUCT_TYPE;

public enum ProductType {
    BREAD, OTHER;

    public static ProductType from(String value) {
        if (value == null) {
            return null;
        }

        return Stream.of(ProductType.values())
                .filter(status -> status.name().equalsIgnoreCase(value))
                .findFirst()
                .orElseThrow(() -> new OwnerException(INVALID_PRODUCT_TYPE));
    }
}
