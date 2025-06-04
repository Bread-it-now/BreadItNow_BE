package com.breaditnow.customer.product.infrastructure.jpa;

import com.breaditnow.customer.product.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface JpaProductRepository extends JpaRepository<ProductEntity, Long> {
    @Modifying
    @Query("UPDATE ProductEntity p SET p.favoriteCount = p.favoriteCount + 1 WHERE p.id = :productId")
    void updateFavoriteProductCount(Long productId);
}
