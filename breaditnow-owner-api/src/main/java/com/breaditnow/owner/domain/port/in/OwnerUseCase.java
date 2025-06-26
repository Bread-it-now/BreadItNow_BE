package com.breaditnow.owner.domain.port.in;

import com.breaditnow.owner.domain.model.Owner;

public interface OwnerUseCase {
    void changePassword(Long ownerId, String newPassword);
    void updateFcmToken(Long ownerId, String fcmToken);
    Owner findOwnerById(Long ownerId);
}
