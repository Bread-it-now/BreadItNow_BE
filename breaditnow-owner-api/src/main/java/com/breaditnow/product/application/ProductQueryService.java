package com.breaditnow.product.application;

import com.breaditnow.owner.application.OwnerDomainProvider;
import com.breaditnow.product.application.port.in.GetProductUseCase;
import com.breaditnow.product.application.port.in.ListProductsUseCase;
import com.breaditnow.product.application.port.out.ProductQueryRepositoryPort;
import com.breaditnow.product.application.port.out.ProductRepositoryPort;
import com.breaditnow.product.infrastructure.adapter.in.presentation.request.ProductSearchCondition;
import com.breaditnow.product.infrastructure.adapter.in.presentation.response.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductQueryService implements ListProductsUseCase, GetProductUseCase {
    private final OwnerDomainProvider ownerDomainProvider;
    private final ProductRepositoryPort productRepositoryPort;
    private final ProductQueryRepositoryPort productQueryRepositoryPort;

    @Override
    public List<ProductResponse> listProducts(Long ownerId, Long bakeryId, ProductSearchCondition condition) {
        ownerDomainProvider.getValidatedBakery(ownerId, bakeryId);
        return productQueryRepositoryPort.search(bakeryId, condition).stream()
                .map(ProductResponse::of)
                .toList();
    }

    @Override
    public ProductResponse getProductDetail(Long ownerId, Long bakeryId, Long productId) {
        ownerDomainProvider.getValidatedBakery(ownerId, bakeryId);
        return ProductResponse.of(productRepositoryPort.getById(productId));
    }
}
