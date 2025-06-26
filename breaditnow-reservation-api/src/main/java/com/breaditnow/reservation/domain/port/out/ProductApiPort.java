package com.breaditnow.reservation.domain.port.out;

import com.breaditnow.reservation.application.dto.internal.ProductInfo;

import java.util.List;
import java.util.Map;

public interface ProductApiPort {
    Map<Long, ProductInfo> findProductsByIds(List<Long> productIds, Long bakeryId);
}
