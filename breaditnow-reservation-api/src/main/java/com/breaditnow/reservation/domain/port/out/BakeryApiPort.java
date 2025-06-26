package com.breaditnow.reservation.domain.port.out;

import com.breaditnow.reservation.application.dto.internal.BakeryInfo;

import java.util.Optional;

public interface BakeryApiPort {
    Optional<BakeryInfo> findBakeryById(Long bakeryId);
}
