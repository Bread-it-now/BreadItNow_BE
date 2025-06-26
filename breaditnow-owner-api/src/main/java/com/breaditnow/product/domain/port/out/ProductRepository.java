package com.breaditnow.product.domain.port.out;

import com.breaditnow.product.domain.model.Product;

import java.util.List;

public interface ProductRepository {
    Product save(Product product);
    void saveAll(List<Product> products);

    Product getById(Long productId);
    List<Product> findAllByIdInAndBakeryId(List<Long> productIds, Long bakeryId);

    Integer findLastDisplayOrderByBakeryId(Long bakeryId);
}
