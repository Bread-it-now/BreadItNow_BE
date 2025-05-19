package com.breaditnow.customer.domain.customer.infrastructure;

import com.breaditnow.customer.domain.customer.core.port.CustomerProductCategoryPort;
import com.breaditnow.customer.domain.customer.core.Customer;
import com.breaditnow.customer.domain.customer.infrastructure.entity.CustomerProductCategoryEntity;
import com.breaditnow.customer.domain.customer.infrastructure.jpa.JpaCustomerProductCategoryRepository;
import com.breaditnow.customer.domain.product.core.ProductCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CustomerProductCategoryAdapter implements CustomerProductCategoryPort {
    private final JpaCustomerProductCategoryRepository jpaCustomerProductCategoryRepository;

    @Override
    public void preference(Customer customer, ProductCategory productCategory) {
        CustomerProductCategoryEntity entity = new CustomerProductCategoryEntity(customer, productCategory);
        jpaCustomerProductCategoryRepository.save(entity);
    }
}
