package com.breaditnow.product.domain.port.out;

import com.breaditnow.product.domain.model.Product;
import com.breaditnow.product.adapter.in.dto.request.ProductSearchCondition;

import java.util.List;

public interface ProductQueryRepositoryPort {
    List<Product> search(Long bakeryId, ProductSearchCondition condition);
}
