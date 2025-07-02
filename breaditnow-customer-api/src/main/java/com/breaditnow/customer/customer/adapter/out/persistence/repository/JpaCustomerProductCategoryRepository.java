package com.breaditnow.customer.customer.adapter.out.persistence.repository;

import com.breaditnow.customer.customer.adapter.out.persistence.entity.CustomerProductCategoryEntity;
import com.breaditnow.customer.customer.adapter.out.persistence.entity.CustomerProductCategoryIdEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaCustomerProductCategoryRepository extends JpaRepository<CustomerProductCategoryEntity, CustomerProductCategoryIdEntity> {
}
