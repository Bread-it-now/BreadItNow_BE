package com.breaditnow.owner.product.application.service;

import com.breaditnow.owner.common.service.OwnerDomainProvider;
import com.breaditnow.owner.product.application.port.in.UpdateProductDisplayOrderUseCase;
import com.breaditnow.owner.product.application.port.in.UpdateProductStatusUseCase;
import com.breaditnow.owner.product.application.port.in.UpdateProductStockUseCase;
import com.breaditnow.owner.product.application.port.in.UpdateProductsStatusUseCase;
import com.breaditnow.owner.product.application.port.out.ProductRepository;
import com.breaditnow.owner.product.domain.Product;
import com.breaditnow.owner.product.domain.ProductReorderer;
import com.breaditnow.owner.product.infrastructure.presentation.request.ProductDisplayOrderUpdateRequest;
import com.breaditnow.owner.product.infrastructure.presentation.request.ProductDisplayOrderUpdateRequest.ProductOrder;
import com.breaditnow.owner.product.infrastructure.presentation.request.ProductStatusUpdateRequest;
import com.breaditnow.owner.product.infrastructure.presentation.request.ProductStockUpdateRequest;
import com.breaditnow.owner.product.infrastructure.presentation.request.ProductsStatusUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
public class ProductStateService implements UpdateProductStockUseCase, UpdateProductStatusUseCase, UpdateProductsStatusUseCase, UpdateProductDisplayOrderUseCase {
    private final OwnerDomainProvider ownerDomainProvider;
    private final ProductRepository productRepository;
    private final ProductReorderer productReorderer;

    @Override
    @Transactional
    public void updateProductStock(Long ownerId, Long bakeryId, Long productId, ProductStockUpdateRequest request) {
        updateSingleProduct(ownerId, bakeryId, productId, product -> product.updateStock(request.stock()));
    }

    @Override
    @Transactional
    public void updateProductStatus(Long ownerId, Long bakeryId, Long productId, ProductStatusUpdateRequest request) {
        updateSingleProduct(ownerId, bakeryId, productId, product -> product.changeStatus(request.status()));
    }

    @Override
    @Transactional
    public void updateProductsStatus(Long ownerId, Long bakeryId, ProductsStatusUpdateRequest request) {
        List<Product> products = ownerDomainProvider.getValidatedProducts(ownerId, bakeryId, request.productIds());
        products.forEach(product -> product.changeStatus(request.status()));
        productRepository.saveAll(products);
    }

    @Override
    @Transactional
    public void updateProductDisplayOrder(Long ownerId, Long bakeryId, ProductDisplayOrderUpdateRequest request) {
        List<Long> productIds = request.products().stream()
                .map(ProductOrder::productId)
                .toList();

        List<Product> productsToUpdate = ownerDomainProvider.getValidatedProducts(ownerId, bakeryId, productIds);

        productReorderer.reorder(productsToUpdate, request.products());
        productRepository.saveAll(productsToUpdate);
    }

    private void updateSingleProduct(Long ownerId, Long bakeryId, Long productId, Consumer<Product> updater) {
        Product product = ownerDomainProvider.getValidatedProduct(ownerId, bakeryId, productId);
        updater.accept(product);
        productRepository.save(product);
    }
}
