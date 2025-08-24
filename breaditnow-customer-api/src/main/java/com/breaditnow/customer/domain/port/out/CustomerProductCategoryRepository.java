package com.breaditnow.customer.domain.port.out;

import com.breaditnow.customer.domain.model.Customer;
import com.breaditnow.product.domain.ProductCategory;

public interface CustomerProductCategoryRepository {
    void preference(Customer customer, ProductCategory productCategory);
}
