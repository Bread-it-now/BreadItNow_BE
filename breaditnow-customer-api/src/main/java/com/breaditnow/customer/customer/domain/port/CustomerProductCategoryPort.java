package com.breaditnow.customer.customer.domain.port;

import com.breaditnow.customer.customer.domain.Customer;
import com.breaditnow.customer.product.domain.ProductCategory;

public interface CustomerProductCategoryPort {
    void preference(Customer customer, ProductCategory productCategory);
}
