package com.breaditnow.owner.product.application.port.in;

import com.breaditnow.owner.product.infrastructure.presentation.response.ProductDetailResponse;

public interface GetProductDetailUseCase {
    ProductDetailResponse getProductDetail(Long ownerId, Long bakeryId, Long productId);
}
