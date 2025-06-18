package com.breaditnow.owner.bakery.application.port.out;


import com.breaditnow.owner.bakery.domain.Bakery;

import java.util.Optional;

public interface BakeryRepository {
    Optional<Bakery> findById(Long bakeryId);
    Bakery getById(Long bakeryId);
    Optional<Bakery> findByIdWithImages(Long bakeryId);
    Long save(Bakery bakery);
}
