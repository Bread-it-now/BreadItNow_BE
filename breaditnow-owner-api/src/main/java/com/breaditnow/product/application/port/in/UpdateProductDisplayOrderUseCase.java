package com.breaditnow.product.application.port.in;

import com.breaditnow.product.infrastructure.adapter.in.presentation.request.ProductDisplayOrderUpdateRequest;

public interface UpdateProductDisplayOrderUseCase {
    void updateProductDisplayOrder(Long ownerId, Long bakeryId, ProductDisplayOrderUpdateRequest request);
}