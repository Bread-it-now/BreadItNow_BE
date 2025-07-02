package com.breaditnow.bakery.domain.port.in;

import com.breaditnow.bakery.domain.model.Bakery;

public interface GetBakeryDetailsUseCase {
    Bakery getBakeryDetails(Long bakeryId);
}
