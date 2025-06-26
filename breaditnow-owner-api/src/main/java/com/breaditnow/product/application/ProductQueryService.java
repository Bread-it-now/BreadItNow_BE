package com.breaditnow.product.application;

import com.breaditnow.owner.application.OwnerDomainProvider;
import com.breaditnow.product.domain.model.Product;
import com.breaditnow.product.domain.port.in.GetProductInfoListUseCase;
import com.breaditnow.product.domain.port.in.GetProductUseCase;
import com.breaditnow.product.domain.port.in.ListProductsUseCase;
import com.breaditnow.product.domain.port.out.ProductQueryRepository;
import com.breaditnow.product.domain.port.out.ProductRepository;
import com.breaditnow.product.application.dto.request.ProductSearchCondition;
import com.breaditnow.product.application.dto.response.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductQueryService implements ListProductsUseCase, GetProductUseCase, GetProductInfoListUseCase {
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

    @Override
    public List<Product> findAllByIds(List<Long> ids, Long bakeryId) {
        return productRepository.findAllByIdInAndBakeryId(ids, bakeryId);
    }
}
