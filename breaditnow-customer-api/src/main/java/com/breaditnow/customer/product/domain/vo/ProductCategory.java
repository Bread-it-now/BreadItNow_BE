package com.breaditnow.customer.product.domain.vo;

import com.breaditnow.customer.common.exception.CustomerException;

import static com.breaditnow.customer.common.exception.CustomerErrorCode.INVALID_PRODUCT_CATEGORY;

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
