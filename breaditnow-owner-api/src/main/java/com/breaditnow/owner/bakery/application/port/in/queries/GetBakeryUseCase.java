package com.breaditnow.owner.bakery.application.port.in.queries;

import com.breaditnow.owner.bakery.domain.Bakery;

public interface GetBakeryUseCase {
    Bakery getBakery(Long bakeryId);
}
