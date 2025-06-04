package com.breaditnow.customer.product.application.port;

import com.breaditnow.customer.product.domain.ProductCategory;

import java.util.List;

public interface LoadProductCategoryPort {
    List<ProductCategory> findAllByIds(List<Long> productCategoryIds);
}
