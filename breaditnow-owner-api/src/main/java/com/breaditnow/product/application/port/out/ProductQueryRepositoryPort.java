package com.breaditnow.product.application.port.out;

import com.breaditnow.product.domain.Product;
import com.breaditnow.product.infrastructure.adapter.in.presentation.request.ProductSearchCondition;

import java.util.List;

public interface ProductQueryRepositoryPort {
    List<Product> search(Long bakeryId, ProductSearchCondition condition);
}
