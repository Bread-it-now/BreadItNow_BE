package com.breaditnow.alert.domain;

import com.breaditnow.common.exception.CustomerException;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import static com.breaditnow.common.exception.CustomerErrorCode.ALREADY_FAVORITED;
import static com.breaditnow.common.exception.CustomerErrorCode.NOT_FAVORITED;

@Getter
@EqualsAndHashCode
public class ProductAlert {
    private final Long customerId;
    private final Long productId;
    private boolean isActive;

    private ProductAlert(Long customerId, Long productId, boolean isActive) {
        this.customerId = customerId;
        this.productId = productId;
        this.isActive = isActive;
    }

    public static ProductAlert create(Long customerId, Long productId) {
        return new ProductAlert(customerId, productId, true);
    }

    public static ProductAlert from(Long customerId, Long productId, boolean active) {
        return new ProductAlert(customerId, productId, active);
    }

    public void toggle() {
        this.isActive = !this.isActive;
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

