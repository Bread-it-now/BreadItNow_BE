package com.breaditnow.customer.bakery.infrastructure.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface JpaBakeryRepository extends JpaRepository<BakeryEntity, Long> {
    @Modifying(clearAutomatically = true)
    @Query("UPDATE BakeryEntity p SET p.favoriteCount = p.favoriteCount + 1 WHERE p.id = :bakeryId")
    void increaseFavoriteCount(Long bakeryId);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE BakeryEntity p SET p.favoriteCount = p.favoriteCount - 1 WHERE p.id = :bakeryId")
    void decreaseFavoriteCount(Long bakeryId);
}
