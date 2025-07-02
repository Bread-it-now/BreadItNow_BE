package com.breaditnow.bakery.domain.port.out;

import com.breaditnow.bakery.domain.model.Bakery;

import java.util.Optional;

public interface BakeryRepository {
    Optional<Bakery> findById(Long bakeryId);
    Optional<Bakery> findByIdWithImages(Long bakeryId);
    Bakery save(Bakery bakery);
}
