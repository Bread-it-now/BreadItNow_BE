package com.breaditnow.product.domain.port.in;

public interface DeleteProductUseCase {
    void deleteProduct(Long ownerId, Long bakeryId, Long productId);
}
