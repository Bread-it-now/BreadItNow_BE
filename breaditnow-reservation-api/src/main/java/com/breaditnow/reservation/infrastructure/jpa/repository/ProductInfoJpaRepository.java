package com.breaditnow.reservation.infrastructure.jpa.repository;

import com.breaditnow.reservation.domain.ProductInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductInfoJpaRepository extends JpaRepository<ProductInfo, Long> {
    List<ProductInfo> findByProductIdIn(List<Long> productIds);
}
