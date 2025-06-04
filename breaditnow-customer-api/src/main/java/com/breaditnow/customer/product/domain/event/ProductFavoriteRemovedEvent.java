package com.breaditnow.customer.product.domain.event;

import lombok.Getter;

@Getter
public class ProductFavoriteRemovedEvent {
    private final Long productId;

    public ProductFavoriteRemovedEvent(Long productId) {
        this.productId = productId;
    }
}

