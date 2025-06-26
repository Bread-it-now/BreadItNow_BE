package com.breaditnow.product.adapter.out.persistence.repository;


import com.breaditnow.product.adapter.out.persistence.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface JpaProductRepository extends JpaRepository<ProductEntity, Long> {
    @Query("SELECT MAX(p.displayOrder) FROM ProductEntity p WHERE p.bakeryId = :bakeryId")
    Optional<Integer> findLastDisplayOrderByBakeryId(@Param("bakeryId") Long bakeryId);

    List<ProductEntity> findAllByIdInAndBakeryId(List<Long> productIds, Long bakeryId);
}
