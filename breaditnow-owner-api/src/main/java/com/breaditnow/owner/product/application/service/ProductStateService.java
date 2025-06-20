package com.breaditnow.owner.product.application.service;

import com.breaditnow.owner.global.exception.OwnerException;
import com.breaditnow.owner.product.application.port.in.UpdateProductDisplayOrderUseCase;
import com.breaditnow.owner.product.application.port.in.UpdateProductStatusUseCase;
import com.breaditnow.owner.product.application.port.in.UpdateProductStockUseCase;
import com.breaditnow.owner.product.application.port.in.UpdateProductsStatusUseCase;
import com.breaditnow.owner.product.application.port.out.ProductRepository;
import com.breaditnow.owner.product.domain.Product;
import com.breaditnow.owner.product.infrastructure.presentation.request.ProductDisplayOrderUpdateRequest;
import com.breaditnow.owner.product.infrastructure.presentation.request.ProductStatusUpdateRequest;
import com.breaditnow.owner.product.infrastructure.presentation.request.ProductStockUpdateRequest;
import com.breaditnow.owner.product.infrastructure.presentation.request.ProductsStatusUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.breaditnow.owner.global.exception.OwnerErrorCode.*;

@Service
@RequiredArgsConstructor
public class ProductStateService implements UpdateProductStockUseCase, UpdateProductStatusUseCase, UpdateProductsStatusUseCase, UpdateProductDisplayOrderUseCase {
    private final OwnershipValidator validator;
    private final ProductRepository productRepository;

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
        validator.getValidatedBakery(ownerId, bakeryId);
        List<Product> products = productRepository.findAllByIdInAndBakeryId(request.productIds(), bakeryId);

        if (products.size() != request.productIds().size()) {
            throw new OwnerException(PRODUCT_NOT_IN_BAKERY);
        }

        products.forEach(product -> product.changeStatus(request.status()));
        productRepository.saveAll(products);
    }

    @Override
    @Transactional
    public void updateProductDisplayOrder(Long ownerId, Long bakeryId, ProductDisplayOrderUpdateRequest request) {
        final List<ProductDisplayOrderUpdateRequest.ProductOrder> requestedOrders = request.products();

        final List<Long> productIds = requestedOrders.stream()
                .map(ProductDisplayOrderUpdateRequest.ProductOrder::productId)
                .toList();
        long distinctIdCount = productIds.stream().distinct().count();
        if (productIds.size() != distinctIdCount) {
            throw new OwnerException(DUPLICATE_PRODUCT_ID_IN_REQUEST);
        }

        final List<Integer> displayOrders = requestedOrders.stream()
                .map(ProductDisplayOrderUpdateRequest.ProductOrder::displayOrder)
                .toList();
        long distinctOrderCount = displayOrders.stream().distinct().count();
        if (displayOrders.size() != distinctOrderCount) {
            throw new OwnerException(DUPLICATE_DISPLAY_ORDER);
        }

        validator.getValidatedBakery(ownerId, bakeryId);

        List<Product> productsToUpdate = productRepository.findAllByIdInAndBakeryId(productIds, bakeryId);

        if (productsToUpdate.size() != requestedOrders.size()) {
            throw new OwnerException(PRODUCT_NOT_IN_BAKERY);
        }

        Map<Long, Product> productMap = productsToUpdate.stream()
                .collect(Collectors.toMap(Product::getId, product -> product));

        requestedOrders.forEach(orderInfo -> {
            Product product = productMap.get(orderInfo.productId());
            product.updateDisplayOrder(orderInfo.displayOrder());
        });

        productRepository.saveAll(productsToUpdate);
    }
}
