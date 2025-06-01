package com.breaditnow.customer.alert.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class ProductAlert {
    private final Long customerId;
    private final Long productId;
    private boolean active;

    private ProductAlert(Long customerId, Long productId, boolean active) {
        this.customerId = customerId;
        this.productId = productId;
        this.active = active;
    }

    public static ProductAlert create(Long customerId, Long productId) {
        return new ProductAlert(customerId, productId, true);
    }

    public static ProductAlert from(Long customerId, Long productId, boolean active) {
        return new ProductAlert(customerId, productId, active);
    }

    public void toggle() {
        this.active = !this.active;
    }
}

