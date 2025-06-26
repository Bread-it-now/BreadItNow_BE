package com.breaditnow.product.domain.port.in;

import com.breaditnow.product.adapter.in.dto.response.ProductResponse;

public interface GetProductUseCase {
    ProductResponse getProductDetail(Long ownerId, Long bakeryId, Long productId);
}
