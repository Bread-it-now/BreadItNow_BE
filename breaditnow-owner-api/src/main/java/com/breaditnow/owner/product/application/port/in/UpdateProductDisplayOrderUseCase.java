package com.breaditnow.owner.product.application.port.in;

import com.breaditnow.owner.product.infrastructure.adapter.in.presentation.request.ProductDisplayOrderUpdateRequest;

public interface UpdateProductDisplayOrderUseCase {
    void updateProductDisplayOrder(Long ownerId, Long bakeryId, ProductDisplayOrderUpdateRequest request);
}