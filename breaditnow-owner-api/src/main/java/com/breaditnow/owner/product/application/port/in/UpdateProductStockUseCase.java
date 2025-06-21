package com.breaditnow.owner.product.application.port.in;

import com.breaditnow.owner.product.infrastructure.presentation.request.ProductStockUpdateRequest;

public interface UpdateProductStockUseCase {
    void updateProductStock(Long ownerId, Long bakeryId, Long productId, ProductStockUpdateRequest request);
}
