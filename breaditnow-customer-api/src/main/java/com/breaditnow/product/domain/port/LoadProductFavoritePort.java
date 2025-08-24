package com.breaditnow.product.domain.port;

import com.breaditnow.product.domain.ProductFavorite;

import java.util.Optional;

public interface LoadProductFavoritePort {
    Optional<ProductFavorite> findProductFavorite(Long customerId, Long productId);
}
