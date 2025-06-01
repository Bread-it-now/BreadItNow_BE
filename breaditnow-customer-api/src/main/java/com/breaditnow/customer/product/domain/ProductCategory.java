package com.breaditnow.customer.product.domain;

import lombok.Getter;

@Getter
public class ProductCategory {
    private Long id;
    private String name;

    public ProductCategory(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
