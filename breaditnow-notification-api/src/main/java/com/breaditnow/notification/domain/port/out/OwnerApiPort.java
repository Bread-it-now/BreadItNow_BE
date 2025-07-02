package com.breaditnow.notification.domain.port.out;

import java.util.Optional;

public interface OwnerApiPort {
    Optional<String> findFcmTokenById(Long ownerId);
}
