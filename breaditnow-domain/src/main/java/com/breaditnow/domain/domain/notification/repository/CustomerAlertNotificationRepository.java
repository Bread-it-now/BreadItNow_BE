package com.breaditnow.domain.domain.notification.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.breaditnow.domain.domain.notification.entity.CustomerAlertNotification;
import com.breaditnow.domain.global.exception.DomainErrorCode;
import com.breaditnow.domain.global.exception.DomainException;

public interface CustomerAlertNotificationRepository extends JpaRepository<CustomerAlertNotification, Long> {
	default CustomerAlertNotification getByCustomerIdAndId(Long customerId, Long alertNotificationId) {
		return findByCustomerIdAndId(customerId, alertNotificationId)
			.orElseThrow(() -> new DomainException(DomainErrorCode.ALERT_NOTIFICATION_NOT_FOUND));
	}

	Optional<CustomerAlertNotification> findByCustomerIdAndId(Long customerId, Long id);
}
