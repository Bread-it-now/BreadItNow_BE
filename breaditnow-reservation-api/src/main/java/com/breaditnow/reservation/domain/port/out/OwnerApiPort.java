package com.breaditnow.reservation.domain.port.out;

import com.breaditnow.reservation.application.dto.internal.BakeryInfo;
import com.breaditnow.reservation.application.dto.internal.ProductInfo;

import java.util.List;
import java.util.Optional;

public interface OwnerApiPort {
    Optional<BakeryInfo> findBakeryById(Long bakeryId);
    List<ProductInfo> findProductsByIds(List<Long> productIds, Long bakeryId);
    Optional<String> findFcmTokenById(Long ownerId);
}
