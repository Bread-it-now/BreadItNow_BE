package com.breaditnow.owner.product.application.port.in;

import com.breaditnow.owner.product.infrastructure.presentation.response.ProductResponse;

public interface GetProductUseCase {
    ProductResponse getProductDetail(Long ownerId, Long bakeryId, Long productId);
}
