package com.breaditnow.notification.application;

import com.breaditnow.common.exception.NotificationException;
import com.breaditnow.notification.adapter.in.web.resolver.AuthenticatedUser;
import com.breaditnow.notification.domain.model.Notification;
import com.breaditnow.notification.domain.port.in.NotificationReadUseCase;
import com.breaditnow.notification.domain.port.out.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.breaditnow.common.exception.NotificationErrorCode.NOTIFICATION_NOT_FOUND;
import static com.breaditnow.common.exception.NotificationErrorCode.UNAUTHORIZED_ACCESS;

@Service
@Transactional
@RequiredArgsConstructor
public class NotificationCommandService implements NotificationReadUseCase {
    private final NotificationRepository notificationRepository;

    @Override
    public void markNotificationAsRead(AuthenticatedUser user, Long bakeryId, Long notificationId) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new NotificationException(NOTIFICATION_NOT_FOUND));

        if (!notification.getBakeryId().equals(bakeryId) || !notification.getUserId().equals(user.userId())) {
            throw new NotificationException(UNAUTHORIZED_ACCESS);
        }
        notification.read();
        notificationRepository.save(notification);
    }
}
