package com.breaditnow.owner.common.service;

import com.breaditnow.owner.bakery.application.port.out.BakeryRepository;
import com.breaditnow.owner.bakery.domain.Bakery;
import com.breaditnow.owner.global.exception.OwnerException;
import com.breaditnow.owner.product.application.port.out.ProductRepository;
import com.breaditnow.owner.product.domain.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.breaditnow.owner.global.exception.OwnerErrorCode.PRODUCT_NOT_FOUND;

@Component
@RequiredArgsConstructor
public class OwnerDomainProvider {
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

    public List<Product> getValidatedProducts(Long ownerId, Long bakeryId, List<Long> productIds) {
        getValidatedBakery(ownerId, bakeryId);

        List<Product> products = productRepository.findAllByIdInAndBakeryId(productIds, bakeryId);
        if (products.size() != productIds.size()) {
            throw new OwnerException(PRODUCT_NOT_FOUND);
        }

        return products;
    }
}
