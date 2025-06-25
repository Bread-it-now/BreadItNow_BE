package com.breaditnow.owner.product.infrastructure.adapter.out.persistence.adapter;

import com.breaditnow.domain.global.exception.DomainException;
import com.breaditnow.owner.product.application.port.out.ProductRepositoryPort;
import com.breaditnow.owner.product.domain.Product;
import com.breaditnow.owner.product.infrastructure.adapter.out.persistence.jpa.JpaProductRepository;
import com.breaditnow.owner.product.infrastructure.adapter.out.persistence.jpa.ProductEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.breaditnow.domain.global.exception.DomainErrorCode.PRODUCT_NOT_FOUND;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryAdapter implements ProductRepositoryPort {
    private final JpaProductRepository jpaProductRepository;

    @Override
    public Product save(Product product) {
        ProductEntity entity = ProductEntity.from(product);
        return jpaProductRepository.save(entity).toDomain();
    }


    @Override
    public void saveAll(List<Product> products) {
        List<ProductEntity> entities = products.stream()
                .map(ProductEntity::from)
                .toList();

        jpaProductRepository.saveAll(entities);
    }

    @Override
    public Integer findLastDisplayOrderByBakeryId(Long bakeryId) {
        return jpaProductRepository.findLastDisplayOrderByBakeryId(bakeryId)
                .orElse(0);
    }

    @Override
    public Product getById(Long productId) {
        return jpaProductRepository.findById(productId)
                .map(ProductEntity::toDomain)
                .orElseThrow(() -> new DomainException(PRODUCT_NOT_FOUND));
    }

    @Override
    public List<Product> findAllByIdInAndBakeryId(List<Long> productIds, Long bakeryId) {
        return jpaProductRepository.findAllByIdInAndBakeryId(productIds, bakeryId).stream()
                .map(ProductEntity::toDomain)
                .toList();
    }
}

