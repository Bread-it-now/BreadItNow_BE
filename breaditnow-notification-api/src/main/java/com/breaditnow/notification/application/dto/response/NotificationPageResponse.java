package com.breaditnow.notification.application.dto.response;

import com.breaditnow.common.domain.PageInfo;
import com.breaditnow.notification.domain.model.Notification;
import org.springframework.data.domain.Page;

import java.util.List;

public record NotificationPageResponse(
        List<NotificationResponse> notifications,
        PageInfo pageInfo
) {

    public static NotificationPageResponse from(Page<Notification> notificationPage) {
        List<NotificationResponse> notificationResponses = notificationPage.getContent().stream()
                .map(NotificationResponse::from)
                .toList();
        return new NotificationPageResponse(notificationResponses, PageInfo.of(notificationPage));
    }

    public record NotificationResponse(
            Long notificationId,
            String title,
            String content,
            boolean isRead
    ) {
        public static NotificationResponse from(Notification notification) {
            return new NotificationResponse(
                    notification.getNotificationId(),
                    notification.getNotificationType().getTitle(),
                    notification.getContent(),
                    notification.isRead()
            );
        }
    }
}
