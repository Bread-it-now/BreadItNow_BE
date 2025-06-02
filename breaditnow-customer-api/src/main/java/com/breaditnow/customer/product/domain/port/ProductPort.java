package com.breaditnow.customer.product.domain.port;


import com.breaditnow.customer.product.domain.Product;

public interface ProductPort {
    Product findById(Long productId);
}
