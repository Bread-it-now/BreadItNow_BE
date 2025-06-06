package com.breaditnow.customer.product.infrastructure;

import com.breaditnow.customer.product.application.port.LoadProductCategoryPort;
import com.breaditnow.customer.product.domain.ProductCategory;
import com.breaditnow.customer.product.infrastructure.jpa.ProductCategoryEntity;
import com.breaditnow.customer.product.infrastructure.jpa.JpaProductCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ProductCategoryAdapter implements LoadProductCategoryPort {
    private final JpaProductCategoryRepository jpaProductCategoryRepository;

    @Override
    public List<ProductCategory> findAllByIds(List<Long> productCategoryIds) {
        List<ProductCategoryEntity> entities = jpaProductCategoryRepository.findAllById(productCategoryIds);

        return entities.stream()
                .map(ProductCategoryEntity::toProductCategory)
                .toList();
    }
}
