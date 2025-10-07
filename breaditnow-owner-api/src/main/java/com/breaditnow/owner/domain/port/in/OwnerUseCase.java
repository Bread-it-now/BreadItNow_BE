package com.breaditnow.owner.domain.port.in;

import com.breaditnow.owner.domain.model.Owner;

import java.util.Optional;

public interface OwnerUseCase {
    void updateFcmToken(Long ownerId, String fcmToken);
    Optional<Owner> findOwnerById(Long ownerId);
    boolean isOwnerInitialized(Long ownerId);
    void createOwnerInit(Long ownerId, String nickname);
}
