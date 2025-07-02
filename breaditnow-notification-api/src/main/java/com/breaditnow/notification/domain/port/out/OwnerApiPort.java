package com.breaditnow.notification.domain.port.out;

import com.breaditnow.notification.application.internal.BakeryInfo;

import java.util.Optional;

public interface OwnerApiPort {
    Optional<String> findFcmTokenById(Long ownerId);
    Optional<BakeryInfo> findBakeryInfoById(Long bakeryId);
}
