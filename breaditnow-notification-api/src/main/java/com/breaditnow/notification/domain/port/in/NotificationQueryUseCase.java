package com.breaditnow.notification.domain.port.in;

import com.breaditnow.notification.adapter.in.web.resolver.AuthenticatedUser;
import com.breaditnow.notification.application.dto.response.NotificationPageResponse;

public interface NotificationQueryUseCase {
    NotificationPageResponse getNotifications(AuthenticatedUser user, Long bakeryId, int page, int size);
}
