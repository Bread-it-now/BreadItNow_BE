package com.breaditnow.product.application.port.in;

import com.breaditnow.product.infrastructure.adapter.in.presentation.response.ProductResponse;

public interface GetProductUseCase {
    ProductResponse getProductDetail(Long ownerId, Long bakeryId, Long productId);
}
