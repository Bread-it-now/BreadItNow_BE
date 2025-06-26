package com.breaditnow.product.domain.port.in;

import com.breaditnow.product.domain.model.Product;

import java.util.List;

public interface GetProductInfoListUseCase {
    List<Product> findAllByIds(List<Long> ids, Long bakeryId);
}
