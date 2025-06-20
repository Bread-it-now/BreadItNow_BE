package com.breaditnow.owner.product.application.port.in;

import com.breaditnow.owner.product.infrastructure.presentation.response.ProductSummaryResponse;

import java.util.List;

public interface ListProductsUseCase {
    List<ProductSummaryResponse> listProducts(Long ownerId, Long bakeryId);
}
