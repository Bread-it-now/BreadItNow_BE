package com.breaditnow.customer.bakery.domain;

import com.breaditnow.customer.common.exception.CustomerException;
import lombok.Getter;

import static com.breaditnow.customer.common.exception.CustomerErrorCode.ALREADY_FAVORITED;
import static com.breaditnow.customer.common.exception.CustomerErrorCode.NOT_FAVORITED;

@Getter
public class BakeryFavorite {
    private Long customerId;
    private Long bakeryId;
    private boolean isActive;

    private BakeryFavorite(Long customerId, Long bakeryId, boolean isActive) {
        this.customerId = customerId;
        this.bakeryId = bakeryId;
        this.isActive = isActive;
    }

    public static BakeryFavorite create(Long customerId, Long bakeryId) {
        return new BakeryFavorite(customerId, bakeryId, true);
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
