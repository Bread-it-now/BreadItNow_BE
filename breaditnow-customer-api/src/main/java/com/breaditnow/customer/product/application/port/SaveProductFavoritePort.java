package com.breaditnow.customer.product.application.port;

import com.breaditnow.customer.customer.domain.Customer;
import com.breaditnow.customer.product.domain.Product;

public interface SaveProductFavoritePort {
    void save(Customer customer, Product product);
}
