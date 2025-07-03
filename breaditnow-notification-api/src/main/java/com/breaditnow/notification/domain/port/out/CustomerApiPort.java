package com.breaditnow.notification.domain.port.out;

import java.util.Optional;

public interface CustomerApiPort {
    Optional<String> findFcmTokenById(Long ownerId);
}
