package com.breaditnow.bakery.infrastructure.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaBakeryFavoriteRepository extends JpaRepository<BakeryFavoriteEntity, BakeryFavoriteEntityId> {
}
