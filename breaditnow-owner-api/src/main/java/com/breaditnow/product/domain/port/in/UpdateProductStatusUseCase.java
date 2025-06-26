package com.breaditnow.product.domain.port.in;

import com.breaditnow.product.adapter.in.dto.request.ProductStatusUpdateRequest;

public interface UpdateProductStatusUseCase {
    void updateProductStatus(Long ownerId, Long bakeryId, Long productId, ProductStatusUpdateRequest request);
}
