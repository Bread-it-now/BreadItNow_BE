package com.breaditnow.owner.product.infrastructure.persistence.adapter;

import com.breaditnow.domain.global.exception.DomainException;
import com.breaditnow.owner.product.application.port.out.ProductRepository;
import com.breaditnow.owner.product.domain.Product;
import com.breaditnow.owner.product.infrastructure.persistence.jpa.JpaProductRepository;
import com.breaditnow.owner.product.infrastructure.persistence.jpa.ProductEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

import static com.breaditnow.domain.global.exception.DomainErrorCode.PRODUCT_NOT_FOUND;

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

    @Override
    public Product getById(Long productId) {
        return productRepository.findById(productId)
                .map(ProductEntity::toDomain)
                .orElseThrow(() -> new DomainException(PRODUCT_NOT_FOUND));
    }

    @Override
    public List<Product> findAllByIdInAndBakeryId(List<Long> productIds, Long bakeryId) {
        return productRepository.findAllByIdInAndBakeryId(productIds, bakeryId).stream()
                .map(ProductEntity::toDomain)
                .toList();
    }

    @Override
    public void saveAll(List<Product> products) {
        List<ProductEntity> entities = products.stream()
                .map(ProductEntity::from)
                .toList();
        productRepository.saveAll(entities);
    }
}
