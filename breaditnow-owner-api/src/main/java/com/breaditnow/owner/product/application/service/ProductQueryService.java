package com.breaditnow.owner.product.application.service;

import com.breaditnow.owner.common.service.OwnerDomainProvider;
import com.breaditnow.owner.product.application.port.in.GetProductDetailUseCase;
import com.breaditnow.owner.product.application.port.in.ListProductsUseCase;
import com.breaditnow.owner.product.application.port.out.ProductRepository;
import com.breaditnow.owner.product.domain.Product;
import com.breaditnow.owner.product.infrastructure.presentation.response.ProductDetailResponse;
import com.breaditnow.owner.product.infrastructure.presentation.response.ProductSummaryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductQueryService implements GetProductDetailUseCase, ListProductsUseCase  {
    private final OwnerDomainProvider ownerDomainProvider;
    private final ProductRepository productRepository;

    @Override
    public ProductDetailResponse getProductDetail(Long ownerId, Long bakeryId, Long productId) {
        Product product = ownerDomainProvider.getValidatedProduct(ownerId, bakeryId, productId);
        return ProductDetailResponse.from(product);
    }

    @Override
    public List<ProductSummaryResponse> listProducts(Long ownerId, Long bakeryId) {
        ownerDomainProvider.getValidatedBakery(ownerId, bakeryId);
        return productRepository.findAllByBakeryIdOrderByDisplayOrderAsc(bakeryId).stream()
                .map(ProductSummaryResponse::from)
                .toList();
    }
}
