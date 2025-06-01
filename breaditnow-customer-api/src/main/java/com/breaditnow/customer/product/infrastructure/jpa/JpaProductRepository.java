package com.breaditnow.customer.product.infrastructure.jpa;

import com.breaditnow.customer.product.infrastructure.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaProductRepository extends JpaRepository<ProductEntity, Long> {
}
