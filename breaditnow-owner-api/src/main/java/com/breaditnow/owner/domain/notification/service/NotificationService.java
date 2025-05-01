package com.breaditnow.owner.domain.notification.service;

import java.util.function.Consumer;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.breaditnow.domain.domain.notification.entity.OwnerReservationNotification;
import com.breaditnow.domain.domain.notification.repository.OwnerReservationNotificationRepository;
import com.breaditnow.owner.domain.notification.controller.res.NotificationPageResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NotificationService {
	private final OwnerReservationNotificationRepository ownerReservationNotificationRepository;

	public NotificationPageResponse getNotifications(Long ownerId, Pageable pageable) {
		Page<OwnerReservationNotification> ownerReservationNotificationPage =
			ownerReservationNotificationRepository.getNotifications(ownerId, pageable);
		return NotificationPageResponse.of(ownerReservationNotificationPage);
	}

	@Transactional
	public Long readAlertNotification(Long ownerId, Long notificationId) {
		updateNotification(ownerId, notificationId, notification -> notification.changeIsRead(true));
		return notificationId;
	}

	@Transactional
	public Long deleteNotification(Long ownerId, Long notificationId) {
		updateNotification(ownerId, notificationId, notification -> notification.changeIsActive(false));
		return notificationId;
	}

	private void updateNotification(Long ownerId, Long notificationId, Consumer<OwnerReservationNotification> updater) {
		OwnerReservationNotification notification = ownerReservationNotificationRepository.getByOwnerIdAndId(ownerId,
			notificationId);
		updater.accept(notification);
	}
}
