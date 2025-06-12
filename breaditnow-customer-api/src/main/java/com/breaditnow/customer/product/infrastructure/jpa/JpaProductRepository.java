package com.breaditnow.customer.product.infrastructure.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaProductRepository extends JpaRepository<ProductEntity, Long> {
    List<ProductEntity> findAllByBakeryId(Long bakeryId);
}
