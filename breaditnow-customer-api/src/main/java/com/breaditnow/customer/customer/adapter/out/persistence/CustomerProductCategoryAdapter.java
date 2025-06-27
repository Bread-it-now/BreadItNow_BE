package com.breaditnow.customer.customer.adapter.out.persistence;

import com.breaditnow.customer.customer.domain.port.out.SaveCustomerProductCategoryPort;
import com.breaditnow.customer.customer.domain.model.Customer;
import com.breaditnow.customer.customer.adapter.out.persistence.entity.CustomerProductCategoryEntity;
import com.breaditnow.customer.customer.adapter.out.persistence.repository.JpaCustomerProductCategoryRepository;
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
