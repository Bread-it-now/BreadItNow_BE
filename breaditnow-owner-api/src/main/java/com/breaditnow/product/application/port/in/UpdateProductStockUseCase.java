package com.breaditnow.product.application.port.in;

import com.breaditnow.product.infrastructure.adapter.in.presentation.request.ProductStockUpdateRequest;

public interface UpdateProductStockUseCase {
    void updateProductStock(Long ownerId, Long bakeryId, Long productId, ProductStockUpdateRequest request);
}
