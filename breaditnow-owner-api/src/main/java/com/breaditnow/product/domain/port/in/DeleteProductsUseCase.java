package com.breaditnow.product.domain.port.in;

import com.breaditnow.product.adapter.in.dto.request.ProductsDeleteRequest;

public interface DeleteProductsUseCase {
    void deleteProducts(Long ownerId, Long bakeryId, ProductsDeleteRequest request);
}
