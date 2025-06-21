package com.breaditnow.owner.product.application.port.in;

import com.breaditnow.owner.product.infrastructure.presentation.request.ProductSearchCondition;
import com.breaditnow.owner.product.infrastructure.presentation.response.ProductResponse;

import java.util.List;

public interface ListProductsUseCase {
    List<ProductResponse> listProducts(Long ownerId, Long bakeryId, ProductSearchCondition condition);
}
