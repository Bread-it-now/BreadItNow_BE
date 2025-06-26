package com.breaditnow.customer.customer.infrastructure;

import com.breaditnow.customer.customer.application.port.out.SaveCustomerProductCategoryPort;
import com.breaditnow.customer.customer.domain.Customer;
import com.breaditnow.customer.customer.infrastructure.jpa.CustomerProductCategoryEntity;
import com.breaditnow.customer.customer.infrastructure.jpa.JpaCustomerProductCategoryRepository;
import com.breaditnow.customer.product.domain.ProductCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CustomerProductCategoryAdapter implements SaveCustomerProductCategoryPort {
    private final JpaCustomerProductCategoryRepository jpaCustomerProductCategoryRepository;

    @Override
    public void preference(Customer customer, ProductCategory productCategory) {
        CustomerProductCategoryEntity entity = new CustomerProductCategoryEntity(customer, productCategory);
        jpaCustomerProductCategoryRepository.save(entity);
    }
}
