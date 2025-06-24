package com.breaditnow.owner.product.application.port.in;

import com.breaditnow.owner.product.infrastructure.adapter.in.presentation.request.ProductSearchCondition;
import com.breaditnow.owner.product.infrastructure.adapter.in.presentation.response.ProductResponse;

import java.util.List;

public interface ListProductsUseCase {
    List<ProductResponse> listProducts(Long ownerId, Long bakeryId, ProductSearchCondition condition);
}
