package com.breaditnow.notification.application.provider;

import com.breaditnow.common.exception.NotificationException;
import com.breaditnow.notification.adapter.in.web.resolver.AuthenticatedUser;
import com.breaditnow.notification.domain.model.Notification;
import com.breaditnow.notification.domain.port.out.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.breaditnow.common.exception.NotificationErrorCode.NOTIFICATION_NOT_FOUND;

@Component
@RequiredArgsConstructor
public class NotificationProvider {
    private final NotificationRepository notificationRepository;

    public Notification provide(AuthenticatedUser user, Long bakeryId, Long notificationId) {
        return notificationRepository.findById(notificationId)
                .filter(notification -> notification.getBakeryId().equals(bakeryId))
                .filter(notification -> notification.getRecipient().userId().equals(user.userId()))
                .orElseThrow(() -> new NotificationException(NOTIFICATION_NOT_FOUND));
    }
}
