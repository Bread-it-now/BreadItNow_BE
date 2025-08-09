package com.breaditnow.bakery.domain.port;

import com.breaditnow.bakery.domain.BakeryFavorite;

public interface SaveBakeryFavoritePort {
    void save(BakeryFavorite bakeryFavorite);
}
