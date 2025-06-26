package com.breaditnow.product.domain.port.in;

import com.breaditnow.product.application.dto.request.ProductDisplayOrderUpdateRequest;

public interface UpdateProductDisplayOrderUseCase {
    void updateProductDisplayOrder(Long ownerId, Long bakeryId, ProductDisplayOrderUpdateRequest request);
}