package com.breaditnow.notification.domain.port.in;

import com.breaditnow.notification.adapter.in.web.resolver.AuthenticatedUser;

public interface NotificationDeleteUseCase {
    void notificationDelete(AuthenticatedUser user, Long bakeryId, Long notificationId);
}
