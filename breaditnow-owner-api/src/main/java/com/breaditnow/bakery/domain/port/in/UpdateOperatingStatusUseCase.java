package com.breaditnow.bakery.domain.port.in;

import com.breaditnow.bakery.domain.model.OperatingStatus;

public interface UpdateOperatingStatusUseCase {
    void updateOperatingStatus(Long ownerId, Long bakeryId, OperatingStatus newStatus);
}
