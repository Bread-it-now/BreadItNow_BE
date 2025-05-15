package com.breaditnow.customer.domain.customer.application.port;

import com.breaditnow.customer.domain.customer.core.Customer;
import com.breaditnow.customer.domain.product.core.ProductCategory;

public interface CustomerProductCategoryPort {
    void preference(Customer customer, ProductCategory productCategory);
}
