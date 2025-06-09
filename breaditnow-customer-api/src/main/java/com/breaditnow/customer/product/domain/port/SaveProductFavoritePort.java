package com.breaditnow.customer.product.domain.port;

import com.breaditnow.customer.product.domain.ProductFavorite;

public interface SaveProductFavoritePort {
    void save(ProductFavorite productFavorite);
}
