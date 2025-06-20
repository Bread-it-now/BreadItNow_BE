package com.breaditnow.owner.product.application.service;

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

@Service
@RequiredArgsConstructor
public class ProductStateService implements UpdateProductStockUseCase, UpdateProductStatusUseCase, UpdateProductsStatusUseCase, UpdateProductDisplayOrderUseCase {
    private final OwnershipValidator validator;
    private final ProductRepository productRepository;
    private final ProductReorderer productReorderer = new ProductReorderer();

    @Override
    @Transactional
    public void updateProductStock(Long ownerId, Long bakeryId, Long productId, ProductStockUpdateRequest request) {
        Product product = validator.getValidatedProduct(ownerId, bakeryId, productId);
        product.updateStock(request.stock());
        productRepository.save(product);
    }

    @Override
    @Transactional
    public void updateProductStatus(Long ownerId, Long bakeryId, Long productId, ProductStatusUpdateRequest request) {
        Product product = validator.getValidatedProduct(ownerId, bakeryId, productId);
        product.changeStatus(request.status());
        productRepository.save(product);
    }

    @Override
    @Transactional
    public void updateProductsStatus(Long ownerId, Long bakeryId, ProductsStatusUpdateRequest request) {
        List<Product> products = validator.getValidatedProducts(ownerId, bakeryId, request.productIds());
        products.forEach(product -> product.changeStatus(request.status()));
        productRepository.saveAll(products);
    }

    @Override
    @Transactional
    public void updateProductDisplayOrder(Long ownerId, Long bakeryId, ProductDisplayOrderUpdateRequest request) {
        List<Long> productIds = request.products().stream()
                .map(ProductOrder::productId)
                .toList();

        List<Product> productsToUpdate = validator.getValidatedProducts(ownerId, bakeryId, productIds);

        productReorderer.reorder(productsToUpdate, request.products());
        productRepository.saveAll(productsToUpdate);
    }
}
