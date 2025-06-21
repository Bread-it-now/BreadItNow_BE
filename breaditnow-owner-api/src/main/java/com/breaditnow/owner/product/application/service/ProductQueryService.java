package com.breaditnow.owner.product.application.service;

import com.breaditnow.owner.owner.application.OwnerDomainProvider;
import com.breaditnow.owner.product.application.port.in.GetProductUseCase;
import com.breaditnow.owner.product.application.port.in.ListProductsUseCase;
import com.breaditnow.owner.product.application.port.out.ProductQueryRepository;
import com.breaditnow.owner.product.application.port.out.ProductRepository;
import com.breaditnow.owner.product.infrastructure.presentation.request.ProductSearchCondition;
import com.breaditnow.owner.product.infrastructure.presentation.response.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductQueryService implements ListProductsUseCase, GetProductUseCase {
    private final OwnerDomainProvider ownerDomainProvider;
    private final ProductRepository productRepository;
    private final ProductQueryRepository productQueryRepository;

    @Override
    public List<ProductResponse> listProducts(Long ownerId, Long bakeryId, ProductSearchCondition condition) {
        ownerDomainProvider.getValidatedBakery(ownerId, bakeryId);
        return productQueryRepository.search(bakeryId, condition).stream()
                .map(ProductResponse::of)
                .toList();
    }

    @Override
    public ProductResponse getProductDetail(Long ownerId, Long bakeryId, Long productId) {
        ownerDomainProvider.getValidatedBakery(ownerId, bakeryId);
        return ProductResponse.of(productRepository.getById(productId));
    }
}
