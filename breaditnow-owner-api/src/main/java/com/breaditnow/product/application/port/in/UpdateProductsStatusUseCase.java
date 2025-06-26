package com.breaditnow.product.application.port.in;

import com.breaditnow.product.infrastructure.adapter.in.presentation.request.ProductsStatusUpdateRequest;

public interface UpdateProductsStatusUseCase {
    void updateProductsStatus(Long ownerId, Long bakeryId, ProductsStatusUpdateRequest request);
}
