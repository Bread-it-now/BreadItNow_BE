package com.breaditnow.owner.domain.notification.service;

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
			ownerReservationNotificationRepository.findByReservationStatus(ownerId, pageable);
		return NotificationPageResponse.of(ownerReservationNotificationPage);
	}

	// private void updateNotification(Long ownerId, Long notificationId, Consumer<CustomerNotification> updater) {
	// 	CustomerNotification notification = ownerReservationNotificationRepository.getByOwnerIdAndId(ow,
	// 		notificationId);
	// 	updater.accept(notification);
	// }

}
