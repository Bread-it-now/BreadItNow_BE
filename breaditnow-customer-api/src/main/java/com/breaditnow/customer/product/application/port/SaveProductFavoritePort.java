package com.breaditnow.customer.product.application.port;

import com.breaditnow.customer.product.domain.ProductFavorite;

public interface SaveProductFavoritePort {
    void save(ProductFavorite productFavorite);
    void delete(ProductFavorite productFavorite);
}
