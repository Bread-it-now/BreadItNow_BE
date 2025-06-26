package com.breaditnow.product.application.port.out;

import com.breaditnow.product.domain.Product;

import java.util.List;

public interface ProductRepositoryPort {
    Product save(Product product);
    void saveAll(List<Product> products);

    Product getById(Long productId);
    List<Product> findAllByIdInAndBakeryId(List<Long> productIds, Long bakeryId);

    Integer findLastDisplayOrderByBakeryId(Long bakeryId);
}
