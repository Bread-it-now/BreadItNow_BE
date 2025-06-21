package com.breaditnow.owner.bakery.application.port.in;

import com.breaditnow.owner.bakery.domain.Bakery;

public interface GetBakeryDetailsUseCase {
    Bakery getBakeryDetails(Long bakeryId);
}
