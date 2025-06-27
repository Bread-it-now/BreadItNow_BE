package com.breaditnow.product.domain.port.in;

import com.breaditnow.product.application.dto.response.ProductInfoResponse;

import java.util.List;

public interface GetProductInfoListUseCase {
    List<ProductInfoResponse> findAllByIds(List<Long> ids, Long bakeryId);
}
