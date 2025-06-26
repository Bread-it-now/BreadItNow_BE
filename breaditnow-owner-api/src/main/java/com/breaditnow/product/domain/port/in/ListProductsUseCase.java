package com.breaditnow.product.domain.port.in;

import com.breaditnow.product.application.dto.request.ProductSearchCondition;
import com.breaditnow.product.application.dto.response.ProductResponse;

import java.util.List;

public interface ListProductsUseCase {
    List<ProductResponse> listProducts(Long ownerId, Long bakeryId, ProductSearchCondition condition);
}
