package com.breaditnow.customer.product.infrastructure;

import com.breaditnow.customer.product.domain.Product;
import com.breaditnow.customer.product.domain.port.ProductPort;
import com.breaditnow.customer.product.infrastructure.entity.ProductEntity;
import com.breaditnow.customer.product.infrastructure.jpa.JpaProductRepository;
import com.breaditnow.domain.global.exception.DomainErrorCode;
import com.breaditnow.domain.global.exception.DomainException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ProductAdapter implements ProductPort {
    private final JpaProductRepository jpaProductRepository;

    @Override
    public Product findById(Long productId) {
        return jpaProductRepository.findById(productId)
                .map(ProductEntity::toDomain)
                .orElseThrow(() -> new DomainException(DomainErrorCode.PRODUCT_NOT_FOUND));
    }
}
