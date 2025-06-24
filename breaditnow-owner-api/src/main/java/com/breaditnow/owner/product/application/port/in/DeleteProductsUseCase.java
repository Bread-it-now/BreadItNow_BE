package com.breaditnow.owner.product.application.port.in;

import com.breaditnow.owner.product.infrastructure.adapter.in.presentation.request.ProductsDeleteRequest;

public interface DeleteProductsUseCase {
    void deleteProducts(Long ownerId, Long bakeryId, ProductsDeleteRequest request);
}
