package com.breaditnow.customer.product.infrastructure.jpa;

import com.breaditnow.customer.product.infrastructure.entity.ProductCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaProductCategory extends JpaRepository<ProductCategoryEntity, Long> {
}
