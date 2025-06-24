package com.breaditnow.owner.product.application.port.in;

import com.breaditnow.owner.product.infrastructure.adapter.in.presentation.request.ProductStatusUpdateRequest;

public interface UpdateProductStatusUseCase {
    void updateProductStatus(Long ownerId, Long bakeryId, Long productId, ProductStatusUpdateRequest request);
}
