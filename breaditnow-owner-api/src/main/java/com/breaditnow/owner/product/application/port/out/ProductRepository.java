package com.breaditnow.owner.product.application.port.out;

import com.breaditnow.owner.product.domain.Product;

public interface ProductRepository {
    Long save(Product product);
    Integer findLastDisplayOrderByBakeryId(Long bakeryId);
    Product getById(Long productId);
}
