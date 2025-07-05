package com.breaditnow.notification.domain.port.in;

import com.breaditnow.notification.adapter.in.web.resolver.AuthenticatedUser;
import com.breaditnow.notification.application.dto.response.NotificationPageResponse;
import com.breaditnow.notification.domain.model.NotificationCategory;

public interface NotificationQueryUseCase {
    NotificationPageResponse getNotifications(AuthenticatedUser user, Long bakeryId, NotificationCategory category, int page, int size);
    NotificationPageResponse getNotifications(AuthenticatedUser user, NotificationCategory category, int page, int size);
}
