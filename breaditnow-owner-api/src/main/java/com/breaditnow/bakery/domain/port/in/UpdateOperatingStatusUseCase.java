package com.breaditnow.bakery.domain.port.in;

import com.breaditnow.common.domain.OperatingStatus;

public interface UpdateOperatingStatusUseCase {
    void updateOperatingStatus(Long ownerId, Long bakeryId, OperatingStatus newStatus);
}
