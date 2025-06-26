package com.breaditnow.product.domain.port.in;

import com.breaditnow.product.adapter.in.dto.request.ProductDisplayOrderUpdateRequest;

public interface UpdateProductDisplayOrderUseCase {
    void updateProductDisplayOrder(Long ownerId, Long bakeryId, ProductDisplayOrderUpdateRequest request);
}