package com.breaditnow.bakery.domain.port.in;


import com.breaditnow.bakery.application.dto.request.BakeryUpdateRequest;

public interface UpdateBakeryUseCase {
    void updateBakery(Long ownerId, Long bakeryId, BakeryUpdateRequest request);
}
