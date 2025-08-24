package com.breaditnow.product.domain.vo;

import com.breaditnow.common.exception.CustomerException;

import static com.breaditnow.common.exception.CustomerErrorCode.INVALID_PRODUCT_CATEGORY;

public enum ProductCategory {
    BREAD, OTHER;

    public static ProductCategory of(String productCategory) {
        if (productCategory == null) return null;

        try {
            return ProductCategory.valueOf(productCategory.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new CustomerException(INVALID_PRODUCT_CATEGORY);
        }
    }
}
