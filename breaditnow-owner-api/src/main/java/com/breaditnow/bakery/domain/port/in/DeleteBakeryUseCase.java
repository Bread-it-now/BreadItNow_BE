package com.breaditnow.bakery.domain.port.in;

public interface DeleteBakeryUseCase {
    void deleteBakery(Long ownerId, Long bakeryId);
}
