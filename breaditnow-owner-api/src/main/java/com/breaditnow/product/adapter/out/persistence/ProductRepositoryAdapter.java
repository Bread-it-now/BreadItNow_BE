package com.breaditnow.product.adapter.out.persistence;

import com.breaditnow.common.exception.OwnerException;
import com.breaditnow.product.domain.port.out.ProductRepository;
import com.breaditnow.product.domain.model.Product;
import com.breaditnow.product.adapter.out.persistence.repository.JpaProductRepository;
import com.breaditnow.product.adapter.out.persistence.entity.ProductEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.breaditnow.common.exception.OwnerErrorCode.PRODUCT_NOT_FOUND;


@Repository
@RequiredArgsConstructor
public class ProductRepositoryAdapter implements ProductRepository {
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
                .orElseThrow(() -> new OwnerException(PRODUCT_NOT_FOUND));
    }

    @Override
    public List<Product> findAllByIdInAndBakeryId(List<Long> productIds, Long bakeryId) {
        return jpaProductRepository.findAllByIdInAndBakeryId(productIds, bakeryId).stream()
                .map(ProductEntity::toDomain)
                .toList();
    }
}

