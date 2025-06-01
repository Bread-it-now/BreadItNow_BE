package com.breaditnow.customer.product.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Product {
    private final Long id;
    private final String name;
    private final Integer price;
    private final boolean isActive;

    @Builder
    public Product(Long id, String name, Integer price, boolean isActive) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.isActive = isActive;
    }
}

