package com.breaditnow.owner.product.application.port.in;

public interface DeleteProductUseCase {
    void deleteProduct(Long ownerId, Long bakeryId, Long productId);
}
