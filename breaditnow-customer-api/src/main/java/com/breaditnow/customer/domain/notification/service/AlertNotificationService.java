package com.breaditnow.customer.domain.notification.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.breaditnow.domain.domain.notification.entity.CustomerAlertNotification;
import com.breaditnow.domain.domain.notification.repository.CustomerAlertNotificationRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AlertNotificationService {
	public final CustomerAlertNotificationRepository notificationRepository;

	@Transactional
	public Long readAlertNotification(Long customerId, Long alertNotificationId) {
		CustomerAlertNotification customerAlertNotification = notificationRepository.getByCustomerIdAndId(customerId,
			alertNotificationId);

		customerAlertNotification.changeIsRead(true);
		return customerAlertNotification.getId();
	}

	@Transactional
	public Long deleteNotification(Long customerId, Long alertNotificationId) {
		CustomerAlertNotification customerAlertNotification = notificationRepository.getByCustomerIdAndId(customerId,
			alertNotificationId);

		customerAlertNotification.changeIsActive(false);
		return customerAlertNotification.getId();
	}
}
