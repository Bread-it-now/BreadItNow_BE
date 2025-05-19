package com.breaditnow.customer.customer.infrastructure.jpa;

import com.breaditnow.customer.customer.infrastructure.entity.CustomerProductCategoryEntity;
import com.breaditnow.customer.customer.infrastructure.entity.CustomerProductCategoryIdEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaCustomerProductCategoryRepository extends JpaRepository<CustomerProductCategoryEntity, CustomerProductCategoryIdEntity> {
}
