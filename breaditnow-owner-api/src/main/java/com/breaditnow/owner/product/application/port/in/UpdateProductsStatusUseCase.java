package com.breaditnow.owner.product.application.port.in;

import com.breaditnow.owner.product.infrastructure.presentation.request.ProductsStatusUpdateRequest;

public interface UpdateProductsStatusUseCase {
    void updateProductsStatus(Long ownerId, Long bakeryId, ProductsStatusUpdateRequest request);
}
