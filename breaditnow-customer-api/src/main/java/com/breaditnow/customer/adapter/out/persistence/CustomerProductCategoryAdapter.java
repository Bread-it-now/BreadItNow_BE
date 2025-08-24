package com.breaditnow.customer.adapter.out.persistence;

import com.breaditnow.customer.domain.port.out.CustomerProductCategoryRepository;
import com.breaditnow.customer.domain.model.Customer;
import com.breaditnow.customer.adapter.out.persistence.entity.CustomerProductCategoryEntity;
import com.breaditnow.customer.adapter.out.persistence.repository.JpaCustomerProductCategoryRepository;
import com.breaditnow.product.domain.ProductCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CustomerProductCategoryAdapter implements CustomerProductCategoryRepository {
    private final JpaCustomerProductCategoryRepository jpaCustomerProductCategoryRepository;

    @Override
    public void preference(Customer customer, ProductCategory productCategory) {
        CustomerProductCategoryEntity entity = new CustomerProductCategoryEntity(customer, productCategory);
        jpaCustomerProductCategoryRepository.save(entity);
    }
}
