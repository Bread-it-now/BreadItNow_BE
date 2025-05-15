package com.breaditnow.customer.domain.product.infrastructure;

import com.breaditnow.customer.domain.product.application.port.ProductCategoryPort;
import com.breaditnow.customer.domain.product.core.ProductCategory;
import com.breaditnow.customer.domain.product.infrastructure.entity.ProductCategoryEntity;
import com.breaditnow.customer.domain.product.infrastructure.jpa.JpaProductCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ProductCategoryAdapter implements ProductCategoryPort {
    private final JpaProductCategory jpaProductCategory;

    @Override
    public List<ProductCategory> findAllByIds(List<Long> productCategoryIds) {
        List<ProductCategoryEntity> entities = jpaProductCategory.findAllById(productCategoryIds);

        return entities.stream()
                .map(ProductCategoryEntity::toProductCategory)
                .toList();
    }
}
