package com.breaditnow.customer.bakery.domain.port;

public interface SaveBakeryPort {
    void increaseFavoriteCount(Long bakeryId);
    void decreaseFavoriteCount(Long bakeryId);
}
