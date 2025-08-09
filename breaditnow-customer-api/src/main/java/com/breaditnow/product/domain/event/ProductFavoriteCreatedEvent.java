package com.breaditnow.product.domain.event;

import lombok.Getter;

@Getter
public class ProductFavoriteCreatedEvent {
    private final Long productId;

    public ProductFavoriteCreatedEvent(Long productId) {
        this.productId = productId;
    }
}

