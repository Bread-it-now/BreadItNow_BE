package com.breaditnow.product.application.port.in;

import com.breaditnow.product.infrastructure.adapter.in.presentation.request.ProductsDeleteRequest;

public interface DeleteProductsUseCase {
    void deleteProducts(Long ownerId, Long bakeryId, ProductsDeleteRequest request);
}
