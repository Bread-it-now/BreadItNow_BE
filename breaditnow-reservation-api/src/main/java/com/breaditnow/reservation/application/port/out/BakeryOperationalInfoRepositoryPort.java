package com.breaditnow.reservation.application.port.out;

import com.breaditnow.reservation.domain.BakeryInfo;

import java.util.Optional;

public interface BakeryOperationalInfoRepositoryPort {
    Optional<BakeryInfo> findByBakeryId(Long bakeryId);
    void save(BakeryInfo bakeryInfo);
}
