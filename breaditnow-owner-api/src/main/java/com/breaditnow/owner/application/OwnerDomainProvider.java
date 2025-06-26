package com.breaditnow.owner.application;

import com.breaditnow.bakery.domain.port.out.BakeryRepository;
import com.breaditnow.bakery.domain.model.Bakery;
import com.breaditnow.common.exception.OwnerErrorCode;
import com.breaditnow.common.exception.OwnerException;
import com.breaditnow.common.support.RepositorySupport;
import com.breaditnow.product.domain.port.out.ProductRepositoryPort;
import com.breaditnow.product.domain.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class OwnerDomainProvider {
    private final BakeryRepository bakeryRepository;
    private final ProductRepositoryPort productRepositoryPort;

    public Bakery getValidatedBakery(Long ownerId, Long bakeryId) {
        Bakery bakery = RepositorySupport.findBakeryOrElseThrow(bakeryRepository, bakeryId);
        bakery.validateOwner(ownerId);
        bakery.validateActive();

        return bakery;
    }

    public Product getValidatedProduct(Long ownerId, Long bakeryId, Long productId) {
        getValidatedBakery(ownerId, bakeryId);
        Product product = productRepositoryPort.getById(productId);
        product.validateBelongsTo(bakeryId);

        return product;
    }

    public List<Product> getValidatedProducts(Long ownerId, Long bakeryId, List<Long> productIds) {
        getValidatedBakery(ownerId, bakeryId);

        List<Product> products = productRepositoryPort.findAllByIdInAndBakeryId(productIds, bakeryId);
        if (products.size() != productIds.size()) {
            throw new OwnerException(OwnerErrorCode.PRODUCT_NOT_FOUND);
        }

        return products;
    }
}
