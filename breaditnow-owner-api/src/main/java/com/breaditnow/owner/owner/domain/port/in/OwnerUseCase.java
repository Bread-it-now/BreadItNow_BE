package com.breaditnow.owner.owner.domain.port.in;

import com.breaditnow.owner.owner.domain.model.Owner;

public interface OwnerUseCase {
    void changePassword(Long ownerId, String newPassword);
    void updateFcmToken(Long ownerId, String fcmToken);
    Owner findOwnerById(Long ownerId);
}
