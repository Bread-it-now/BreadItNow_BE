package com.breaditnow.bakery.domain.port;

public interface SaveBakeryPort {
    void increaseFavoriteCount(Long bakeryId);
    void decreaseFavoriteCount(Long bakeryId);
}
