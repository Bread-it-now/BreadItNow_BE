package com.breaditnow.product.domain.port;

import com.breaditnow.product.domain.Product;

import java.util.Optional;

public interface LoadProductPort {
    Optional<Product> findProduct(Long productId);
    Product getProduct(Long productId);
}
