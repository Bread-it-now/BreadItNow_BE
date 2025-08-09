package com.breaditnow.product.infrastructure.jpa;

import com.breaditnow.product.infrastructure.jpa.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaProductRepository extends JpaRepository<ProductEntity, Long> {
    List<ProductEntity> findAllByBakeryId(Long bakeryId);
}
