package com.breaditnow.customer.domain.product.infrastructure.jpa;

import com.breaditnow.customer.domain.product.infrastructure.entity.ProductCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaProductCategory extends JpaRepository<ProductCategoryEntity, Long> {
}
