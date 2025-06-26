package com.breaditnow.product.domain.port.in;

import com.breaditnow.product.adapter.in.dto.request.ProductsStatusUpdateRequest;

public interface UpdateProductsStatusUseCase {
    void updateProductsStatus(Long ownerId, Long bakeryId, ProductsStatusUpdateRequest request);
}
