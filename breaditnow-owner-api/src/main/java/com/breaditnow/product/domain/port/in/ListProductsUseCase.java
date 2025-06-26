package com.breaditnow.product.domain.port.in;

import com.breaditnow.product.adapter.in.dto.request.ProductSearchCondition;
import com.breaditnow.product.adapter.in.dto.response.ProductResponse;

import java.util.List;

public interface ListProductsUseCase {
    List<ProductResponse> listProducts(Long ownerId, Long bakeryId, ProductSearchCondition condition);
}
