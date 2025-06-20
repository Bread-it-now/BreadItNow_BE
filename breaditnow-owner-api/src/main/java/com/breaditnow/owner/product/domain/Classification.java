package com.breaditnow.owner.product.domain;

import com.breaditnow.owner.global.exception.OwnerException;

import static com.breaditnow.owner.global.exception.OwnerErrorCode.PRODUCT_CATEGORY_TYPE_REQUIRED;

public record Classification(
        ProductType type
) {
    public static Classification create(ProductType productType) {
        if (productType == null) {
            throw new OwnerException(PRODUCT_CATEGORY_TYPE_REQUIRED);
        }
        return new Classification(productType);
    }
}
