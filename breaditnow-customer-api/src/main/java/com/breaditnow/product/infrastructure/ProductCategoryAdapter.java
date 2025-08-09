package com.breaditnow.product.infrastructure;

import com.breaditnow.product.domain.port.ProductCategoryRepository;
import com.breaditnow.product.domain.ProductCategory;
import com.breaditnow.product.infrastructure.jpa.entity.ProductCategoryEntity;
import com.breaditnow.product.infrastructure.jpa.JpaProductCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ProductCategoryAdapter implements ProductCategoryRepository {
    private final JpaProductCategoryRepository jpaProductCategoryRepository;

    @Override
    public List<ProductCategory> findAllByIds(List<Long> productCategoryIds) {
        return jpaProductCategoryRepository.findAllById(productCategoryIds).stream()
                .map(ProductCategoryEntity::toProductCategory)
                .toList();
    }
}
