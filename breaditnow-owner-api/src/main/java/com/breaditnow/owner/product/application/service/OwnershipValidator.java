package com.breaditnow.owner.product.application.service;

import com.breaditnow.owner.bakery.application.port.out.BakeryRepository;
import com.breaditnow.owner.bakery.domain.Bakery;
import com.breaditnow.owner.product.application.port.out.ProductRepository;
import com.breaditnow.owner.product.domain.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OwnershipValidator {
    private final BakeryRepository bakeryRepository;
    private final ProductRepository productRepository;

    public Bakery getValidatedBakery(Long ownerId, Long bakeryId) {
        Bakery bakery = bakeryRepository.getById(bakeryId);
        bakery.validateOwner(ownerId);
        bakery.validateActive();
        return bakery;
    }

    public Product getValidatedProduct(Long ownerId, Long bakeryId, Long productId) {
        getValidatedBakery(ownerId, bakeryId);
        Product product = productRepository.getById(productId);
        product.validateBelongsTo(bakeryId);
        return product;
    }
}
