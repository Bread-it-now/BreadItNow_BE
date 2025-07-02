package com.breaditnow.notification.domain.port.in;

import com.breaditnow.notification.adapter.in.web.resolver.AuthenticatedUser;

public interface NotificationReadUseCase {
    void markNotificationAsRead(AuthenticatedUser user, Long bakeryId, Long notificationId);
}
