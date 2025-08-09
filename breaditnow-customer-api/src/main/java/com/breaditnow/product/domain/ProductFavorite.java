package com.breaditnow.product.domain;

import com.breaditnow.common.exception.CustomerException;
import lombok.Builder;
import lombok.Getter;

import static com.breaditnow.common.exception.CustomerErrorCode.ALREADY_FAVORITED;
import static com.breaditnow.common.exception.CustomerErrorCode.NOT_FAVORITED;

@Getter
public class ProductFavorite {
    private Long customerId;
    private Long productId;
    private boolean isActive;

    @Builder
    private ProductFavorite(Long customerId, Long productId, boolean isActive) {
        this.customerId = customerId;
        this.productId = productId;
        this.isActive = isActive;
    }

    public static ProductFavorite create(Long customerId, Long productId) {
        return new ProductFavorite(customerId, productId, true);
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
