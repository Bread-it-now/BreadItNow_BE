package com.breaditnow.customer.bakery.domain.port;

import com.breaditnow.customer.bakery.domain.BakeryFavorite;

import java.util.Optional;

public interface LoadBakeryFavoritePort {
    Optional<BakeryFavorite> findBakeryFavorite(Long customerId, Long bakeryId);
}
