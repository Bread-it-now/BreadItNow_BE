package com.breaditnow.notification.application.provider;

import com.breaditnow.common.domain.Role;
import com.breaditnow.notification.domain.model.NotificationActor;
import org.springframework.stereotype.Component;

import static com.breaditnow.common.domain.Role.CUSTOMER;
import static com.breaditnow.common.domain.Role.OWNER;

@Component
public class NotificationActorProvider {
    public NotificationActor initiatorOf(Role initiatedBy, Long customerId, Long ownerId) {
        return new NotificationActor(
                initiatedBy == CUSTOMER ? customerId : ownerId,
                initiatedBy
        );
    }

    public NotificationActor recipientOf(Role initiatedBy, Long customerId, Long ownerId) {
        return new NotificationActor(
                initiatedBy == CUSTOMER ? ownerId : customerId,
                initiatedBy == CUSTOMER ? OWNER : CUSTOMER
        );
    }
}
