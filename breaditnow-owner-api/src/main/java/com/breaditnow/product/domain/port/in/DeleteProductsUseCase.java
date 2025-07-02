package com.breaditnow.product.domain.port.in;

import com.breaditnow.product.application.dto.request.ProductsDeleteRequest;

public interface DeleteProductsUseCase {
    void deleteProducts(Long ownerId, Long bakeryId, ProductsDeleteRequest request);
}
