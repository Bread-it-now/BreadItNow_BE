package com.breaditnow.owner.bakery.domain.port;


import com.breaditnow.owner.bakery.domain.Bakery;

import java.util.Optional;

public interface BakeryRepository {
    Optional<Bakery> findById(Long bakeryId);
    Long save(Bakery bakery);
}
