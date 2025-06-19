package com.breaditnow.owner.product.application.port.out;

import com.breaditnow.owner.product.domain.Product;

import java.util.List;

public interface ProductRepository {
    Long save(Product product);
    void saveAll(List<Product> products);
    Integer findLastDisplayOrderByBakeryId(Long bakeryId);
    Product getById(Long productId);
    List<Product> findAllByIdInAndBakeryId(List<Long> productIds, Long bakeryId);
}
