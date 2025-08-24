package com.breaditnow.product.domain.port;

import com.breaditnow.product.domain.ProductCategory;

import java.util.List;

public interface ProductCategoryRepository {
    List<ProductCategory> findAllByIds(List<Long> productCategoryIds);
}
