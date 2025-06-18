package com.breaditnow.owner.product.infrastructure.persistence.adapter;

import com.breaditnow.owner.product.application.port.out.ProductRepository;
import com.breaditnow.owner.product.domain.Product;
import com.breaditnow.owner.product.infrastructure.persistence.jpa.JpaProductRepository;
import com.breaditnow.owner.product.infrastructure.persistence.jpa.ProductEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryAdapter implements ProductRepository {
    private final JpaProductRepository productRepository;

    @Override
    public Long save(Product product) {
        ProductEntity entity = ProductEntity.from(product);
        return productRepository.save(entity).getId();
    }

    @Override
    public Integer findLastDisplayOrderByBakeryId(Long bakeryId) {
        return productRepository.findLastDisplayOrderByBakeryId(bakeryId)
                .orElse(0);
    }
}
