package com.breaditnow.customer.product.infrastructure.jpa;

import com.breaditnow.customer.product.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

import static jakarta.persistence.LockModeType.PESSIMISTIC_WRITE;

public interface JpaProductRepository extends JpaRepository<ProductEntity, Long> {
    @Modifying
    @Query("UPDATE ProductEntity p SET p.favoriteCount = p.favoriteCount + 1 WHERE p.id = :productId")
    void increaseFavoriteProductCount(Long productId);


    @Modifying
    @Query("UPDATE ProductEntity p SET p.favoriteCount = p.favoriteCount - 1 WHERE p.id = :productId")
    void decreaseFavoriteProductCount(Long productId);


    @Lock(PESSIMISTIC_WRITE)
    @Query("SELECT p FROM ProductEntity p WHERE p.id = :productId")
    Optional<ProductEntity> loadProductWithLock(@Param("productId") Long productId);
}
