package com.breaditnow.customer.product.domain.port;

import com.breaditnow.customer.product.domain.ProductFavorite;

import java.util.Optional;

public interface LoadProductFavoritePort {
    Optional<ProductFavorite> loadProductFavorite(Long customerId, Long productId);
}
