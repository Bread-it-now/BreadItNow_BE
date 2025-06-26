package com.breaditnow.customer.customer.application.port.out;

import com.breaditnow.customer.customer.domain.Customer;
import com.breaditnow.customer.product.domain.ProductCategory;

public interface SaveCustomerProductCategoryPort {
    void preference(Customer customer, ProductCategory productCategory);
}
