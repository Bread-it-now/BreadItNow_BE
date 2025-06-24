package com.breaditnow.reservation.application.port.out;

import com.breaditnow.reservation.domain.BakeryOperationalInfo;

import java.util.Optional;

public interface BakeryOperationalInfoRepositoryPort {
    Optional<BakeryOperationalInfo> findByBakeryId(Long bakeryId);
    void save(BakeryOperationalInfo bakeryOperationalInfo);
    void deleteByBakeryId(Long bakeryId);
}
