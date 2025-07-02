package com.breaditnow.bakery.adapter.out.persistence.repository;

import com.breaditnow.bakery.adapter.out.persistence.entity.BakeryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface JpaBakeryRepository extends JpaRepository<BakeryEntity, Long> {
    Optional<BakeryEntity> findByIdAndDeletedFalse(Long bakeryId);

    @Query("select b from BakeryEntity b left join fetch b.additionalImages where b.id = :bakeryId")
    Optional<BakeryEntity> findBakeryWithImagesById(@Param("bakeryId") Long bakeryId);
}
