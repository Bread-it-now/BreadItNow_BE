package com.breaditnow.product.domain.port;

import com.breaditnow.product.domain.ProductFavorite;

public interface SaveProductFavoritePort {
    void save(ProductFavorite productFavorite);
}
