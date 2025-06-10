package com.breaditnow.customer.customer.infrastructure.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaCustomerProductCategoryRepository extends JpaRepository<CustomerProductCategoryEntity, CustomerProductCategoryIdEntity> {
}
