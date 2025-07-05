package com.breaditnow.notification.application.dto.response;

import com.breaditnow.common.domain.PageInfo;
import com.breaditnow.notification.domain.model.Notification;
import com.breaditnow.notification.domain.model.NotificationCategory;
import com.breaditnow.notification.domain.model.NotificationType;
import org.springframework.data.domain.Page;

import java.util.List;

import static com.breaditnow.common.domain.DailyTime.DATE_FORMATTER;

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
            Long reservationId,
            NotificationCategory notificationCategory,
            NotificationType notificationType,
            String content,
            boolean isRead,
            String createdAt
    ) {
        public static NotificationResponse from(Notification notification) {
            return new NotificationResponse(
                    notification.getNotificationId(),
                    notification.getReservationId(),
                    notification.getNotificationCategory(),
                    notification.getNotificationType(),
                    notification.getContent(),
                    notification.isRead(),
                    notification.getCreatedAt().format(DATE_FORMATTER)
            );
        }
    }
}
