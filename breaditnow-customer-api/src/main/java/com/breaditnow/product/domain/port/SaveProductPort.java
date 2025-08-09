package com.breaditnow.product.domain.port;

import com.breaditnow.product.domain.Product;

public interface SaveProductPort {
    void save(Product product);
}
