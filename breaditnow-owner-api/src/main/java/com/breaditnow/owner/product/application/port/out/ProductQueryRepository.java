package com.breaditnow.owner.product.application.port.out;

import com.breaditnow.owner.product.domain.Product;
import com.breaditnow.owner.product.infrastructure.presentation.request.ProductSearchCondition;

import java.util.List;

public interface ProductQueryRepository {
    List<Product> search(Long bakeryId, ProductSearchCondition condition);
}
