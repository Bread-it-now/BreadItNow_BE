package com.breaditnow.bakery.domain.port;

import com.breaditnow.bakery.domain.BakeryFavorite;

import java.util.Optional;

public interface LoadBakeryFavoritePort {
    Optional<BakeryFavorite> findBakeryFavorite(Long customerId, Long bakeryId);
    BakeryFavorite getBakeryFavorite(Long customerId, Long bakeryId);
}
