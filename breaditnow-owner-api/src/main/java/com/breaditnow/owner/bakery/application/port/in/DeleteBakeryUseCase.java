package com.breaditnow.owner.bakery.application.port.in;

public interface DeleteBakeryUseCase {
    void deleteBakery(Long ownerId, Long bakeryId);
}
