package com.breaditnow.owner.bakery.infrastructure.persistence.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaBakeryRepository extends JpaRepository<BakeryEntity, Long> {
    Optional<BakeryEntity> findByIdAndDeletedFalse(Long bakeryId);
}
