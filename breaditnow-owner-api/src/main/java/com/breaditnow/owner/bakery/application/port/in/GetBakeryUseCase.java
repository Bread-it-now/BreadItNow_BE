package com.breaditnow.owner.bakery.application.port.in;

import com.breaditnow.owner.bakery.domain.Bakery;

public interface GetBakeryUseCase {
    Bakery getBakery(Long bakeryId);
}
