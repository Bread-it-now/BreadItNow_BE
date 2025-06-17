package com.breaditnow.owner.bakery.application.port.in;

import com.breaditnow.owner.bakery.domain.OperatingStatus;

public interface UpdateOperatingStatusUseCase {
    void updateOperatingStatus(Long ownerId, Long bakeryId, OperatingStatus newStatus);
}
