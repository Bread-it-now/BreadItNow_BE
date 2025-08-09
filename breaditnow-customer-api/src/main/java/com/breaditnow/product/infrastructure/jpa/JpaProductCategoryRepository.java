package com.breaditnow.product.infrastructure.jpa;

import com.breaditnow.product.infrastructure.jpa.entity.ProductCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaProductCategoryRepository extends JpaRepository<ProductCategoryEntity, Long> {
}
