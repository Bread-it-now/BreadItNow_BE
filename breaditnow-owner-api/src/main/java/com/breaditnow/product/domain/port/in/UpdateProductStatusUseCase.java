package com.breaditnow.product.domain.port.in;

import com.breaditnow.product.application.dto.request.ProductStatusUpdateRequest;

public interface UpdateProductStatusUseCase {
    void updateProductStatus(Long ownerId, Long bakeryId, Long productId, ProductStatusUpdateRequest request);
}
