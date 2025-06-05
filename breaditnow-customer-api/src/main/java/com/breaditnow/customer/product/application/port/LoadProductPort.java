package com.breaditnow.customer.product.application.port;

import com.breaditnow.customer.product.domain.Product;

import java.util.Optional;

public interface LoadProductPort {
    Optional<Product> loadProduct(Long productId);
}
