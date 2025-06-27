package com.breaditnow.customer.customer.domain.port.out;

import com.breaditnow.customer.customer.domain.model.Customer;
import com.breaditnow.customer.product.domain.ProductCategory;

public interface SaveCustomerProductCategoryPort {
    void preference(Customer customer, ProductCategory productCategory);
}
