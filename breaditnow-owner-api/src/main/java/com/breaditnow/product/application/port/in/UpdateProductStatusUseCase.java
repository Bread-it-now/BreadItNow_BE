package com.breaditnow.product.application.port.in;

import com.breaditnow.product.infrastructure.adapter.in.presentation.request.ProductStatusUpdateRequest;

public interface UpdateProductStatusUseCase {
    void updateProductStatus(Long ownerId, Long bakeryId, Long productId, ProductStatusUpdateRequest request);
}
