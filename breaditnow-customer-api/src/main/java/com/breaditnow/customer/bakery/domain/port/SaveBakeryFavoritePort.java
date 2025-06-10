package com.breaditnow.customer.bakery.domain.port;

import com.breaditnow.customer.bakery.domain.BakeryFavorite;

public interface SaveBakeryFavoritePort {
    void save(BakeryFavorite bakeryFavorite);
}
