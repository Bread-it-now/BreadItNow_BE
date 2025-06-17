package com.breaditnow.owner.bakery.application.port.in;


import com.breaditnow.owner.bakery.infrastructure.presentation.request.BakeryUpdateRequest;

public interface UpdateBakeryUseCase {
    void updateBakery(Long ownerId, Long bakeryId, BakeryUpdateRequest request);
}
