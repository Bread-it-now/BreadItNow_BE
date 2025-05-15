package com.breaditnow.customer.domain.customer.infrastructure.jpa;

import com.breaditnow.customer.domain.customer.infrastructure.entity.CustomerProductCategoryEntity;
import com.breaditnow.customer.domain.customer.infrastructure.entity.CustomerProductCategoryIdEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaCustomerProductCategoryRepository extends JpaRepository<CustomerProductCategoryEntity, CustomerProductCategoryIdEntity> {
}
