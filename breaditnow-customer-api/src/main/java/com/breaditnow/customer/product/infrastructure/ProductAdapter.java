package com.breaditnow.customer.product.infrastructure;

import com.breaditnow.customer.product.domain.port.LoadProductPort;
import com.breaditnow.customer.product.domain.port.SaveProductPort;
import com.breaditnow.customer.product.application.request.HotProductSearchCriteria;
import com.breaditnow.customer.product.domain.Product;
import com.breaditnow.customer.product.infrastructure.jpa.JpaProductRepository;
import com.breaditnow.customer.product.infrastructure.jpa.ProductEntity;
import com.breaditnow.customer.product.infrastructure.jpa.QueryProductRepository;
import com.breaditnow.customer.product.presentation.response.HotProductPageResponse;
import com.breaditnow.customer.product.presentation.response.HotProductResponse;
import com.breaditnow.domain.global.exception.DomainException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.breaditnow.domain.global.exception.DomainErrorCode.PRODUCT_NOT_FOUND;

@Repository
@RequiredArgsConstructor
public class ProductAdapter implements LoadProductPort, SaveProductPort {
    private final JpaProductRepository jpaProductRepository;
    private final QueryProductRepository queryProductRepository;

    @Override
    public Optional<Product> findProduct(Long productId) {
        return jpaProductRepository.findById(productId)
                .map(ProductEntity::toDomain);
    }

    @Override
    public Product getProduct(Long productId) {
        return findProduct(productId)
                .orElseThrow(() -> new DomainException(PRODUCT_NOT_FOUND));
    }

    @Override
    public void save(Product product) {
        jpaProductRepository.save(ProductEntity.from(product));
    }

    public HotProductPageResponse getHotProducts(Long customerId, HotProductSearchCriteria hotProductSearchCriteria) {
        Page<HotProductResponse> hotProductResponses = queryProductRepository.fetchHotProducts(customerId, hotProductSearchCriteria);
        return HotProductPageResponse.of(hotProductResponses);
    }
}
