package com.breaditnow.customer.product.domain;

import com.breaditnow.customer.common.exception.CustomerException;
import lombok.Getter;

import static com.breaditnow.customer.common.exception.CustomerErrorCode.ALREADY_FAVORITED;
import static com.breaditnow.customer.common.exception.CustomerErrorCode.NOT_FAVORITED;

@Getter
public class ProductFavorite {
    Long customerId;
    Long productId;
    boolean isActive;

    public ProductFavorite(Long customerId, Long productId) {
        this.customerId = customerId;
        this.productId = productId;
    }

    public void activate() {
        if (isActive) {
            throw new CustomerException(ALREADY_FAVORITED);
        }
        this.isActive = true;
    }

    public void deactivate() {
        if (!isActive) {
            throw new CustomerException(NOT_FAVORITED);
        }
        this.isActive = false;
    }
}
