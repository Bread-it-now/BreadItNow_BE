package com.breaditnow.product.domain.port.out;

import com.breaditnow.product.domain.model.Product;
import com.breaditnow.product.application.dto.request.ProductSearchCondition;

import java.util.List;

public interface ProductQueryRepository {
    List<Product> search(Long bakeryId, ProductSearchCondition condition);
}
