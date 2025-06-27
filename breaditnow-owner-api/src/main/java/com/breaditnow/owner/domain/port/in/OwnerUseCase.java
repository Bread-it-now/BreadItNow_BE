package com.breaditnow.owner.domain.port.in;

import com.breaditnow.owner.domain.model.Owner;

import java.util.Optional;

public interface OwnerUseCase {
    void changePassword(Long ownerId, String newPassword);
    void updateFcmToken(Long ownerId, String fcmToken);
    Optional<Owner> findOwnerById(Long ownerId);
}
