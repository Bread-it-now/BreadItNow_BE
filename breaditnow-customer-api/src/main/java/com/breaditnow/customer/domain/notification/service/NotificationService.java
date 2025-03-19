package com.breaditnow.customer.domain.notification.service;

import static com.breaditnow.domain.domain.notification.enumerate.NotificationType.*;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.breaditnow.customer.domain.notification.controller.res.NotificationPageResponse;
import com.breaditnow.domain.domain.notification.entity.CustomerAlertNotification;
import com.breaditnow.domain.domain.notification.entity.CustomerReservationNotification;
import com.breaditnow.domain.domain.notification.enumerate.NotificationType;
import com.breaditnow.domain.domain.notification.repository.CustomerAlertNotificationRepository;
import com.breaditnow.domain.domain.notification.repository.CustomerReservationNotificationRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class NotificationService {
	private final CustomerAlertNotificationRepository alertNotificationRepository;
	private final CustomerReservationNotificationRepository reservationNotificationRepository;

	@Transactional
	public Long readAlertNotification(Long customerId, Long notificationId,
		List<NotificationType> notificationTypes) {
		for (NotificationType type : notificationTypes) {
			if (type.equals(ALERT)) {
				CustomerAlertNotification customerAlertNotification = alertNotificationRepository.getByCustomerIdAndId(
					customerId,
					notificationId);
				customerAlertNotification.changeIsRead(true);
			} else if (type.equals(RESERVATION)) {
				CustomerReservationNotification customerReservationNotification = reservationNotificationRepository.getByCustomerIdAndId(
					customerId,
					notificationId);
				customerReservationNotification.changeIsRead(true);
			}
		}
		return notificationId;
	}

	@Transactional
	public Long deleteNotification(Long customerId, Long notificationId,
		List<NotificationType> notificationTypes) {

		for (NotificationType type : notificationTypes) {
			if (type.equals(ALERT)) {
				CustomerAlertNotification customerAlertNotification = alertNotificationRepository.getByCustomerIdAndId(
					customerId,
					notificationId);
				customerAlertNotification.changeIsActive(false);
			} else if (type.equals(RESERVATION)) {
				CustomerReservationNotification customerReservationNotification = reservationNotificationRepository.getByCustomerIdAndId(
					customerId,
					notificationId);
				customerReservationNotification.changeIsActive(false);
			}
		}
		return notificationId;
	}

	public NotificationPageResponse getNotifications(Long customerId, Pageable pageable,
		List<NotificationType> notificationTypes) {
		return null;
	}
}
