package com.breaditnow.notification.application;

import com.breaditnow.notification.adapter.in.web.resolver.AuthenticatedUser;
import com.breaditnow.notification.application.provider.NotificationProvider;
import com.breaditnow.notification.domain.model.Notification;
import com.breaditnow.notification.domain.port.in.NotificationDeleteUseCase;
import com.breaditnow.notification.domain.port.in.NotificationReadUseCase;
import com.breaditnow.notification.domain.port.out.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class NotificationManagementService implements NotificationReadUseCase, NotificationDeleteUseCase {
    private final NotificationProvider notificationProvider;
    private final NotificationRepository notificationRepository;

    @Override
    public void markNotificationAsRead(AuthenticatedUser user, Long bakeryId, Long notificationId) {
        Notification notification = notificationProvider.provide(user, bakeryId, notificationId);
        notification.read();
        notificationRepository.save(notification);
    }

    @Override
    public void notificationDelete(AuthenticatedUser user, Long bakeryId, Long notificationId) {
        Notification notification = notificationProvider.provide(user, bakeryId, notificationId);
        notification.delete();
        notificationRepository.save(notification);
    }
}
