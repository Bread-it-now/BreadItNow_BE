package com.breaditnow.bakery.domain.port.in;

import com.breaditnow.bakery.domain.model.Bakery;

import java.util.Optional;

public interface GetBakeryInfoUseCase {
    Optional<Bakery> findById(Long bakeryId);
}
