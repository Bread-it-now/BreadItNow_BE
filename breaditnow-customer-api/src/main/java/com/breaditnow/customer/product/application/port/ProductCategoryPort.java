package com.breaditnow.customer.product.application.port;

import com.breaditnow.customer.product.core.ProductCategory;

import java.util.List;

public interface ProductCategoryPort {
    List<ProductCategory> findAllByIds(List<Long> productCategoryIds);
}
