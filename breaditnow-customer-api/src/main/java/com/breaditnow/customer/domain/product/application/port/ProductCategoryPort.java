package com.breaditnow.customer.domain.product.application.port;

import com.breaditnow.customer.domain.product.core.ProductCategory;

import java.util.List;

public interface ProductCategoryPort {
    List<ProductCategory> findAllByIds(List<Long> productCategoryIds);
}
