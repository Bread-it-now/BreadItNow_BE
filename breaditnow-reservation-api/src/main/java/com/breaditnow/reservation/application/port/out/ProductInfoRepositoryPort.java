package com.breaditnow.reservation.application.port.out;

import com.breaditnow.reservation.domain.ProductInfo;

import java.util.List;
import java.util.Optional;

public interface ProductInfoRepositoryPort {
    Optional<ProductInfo> findById(Long productId);
    List<ProductInfo> findByProductIdIn(List<Long> productIds);
    void save(ProductInfo productInfo);
    void deleteById(Long productId);
}
