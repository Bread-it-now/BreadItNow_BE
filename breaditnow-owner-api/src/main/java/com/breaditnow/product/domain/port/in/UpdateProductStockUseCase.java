package com.breaditnow.product.domain.port.in;

import com.breaditnow.product.application.dto.request.ProductStockUpdateRequest;

public interface UpdateProductStockUseCase {
    void updateProductStock(Long ownerId, Long bakeryId, Long productId, ProductStockUpdateRequest request);
}
