package com.breaditnow.domain.domain.notification.repository;

import static com.breaditnow.domain.global.exception.DomainErrorCode.*;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.breaditnow.domain.domain.notification.entity.CustomerReservationNotification;
import com.breaditnow.domain.global.exception.DomainException;

public interface CustomerReservationNotificationRepository
	extends JpaRepository<CustomerReservationNotification, Long> {

	default CustomerReservationNotification getByCustomerIdAndId(Long customerId, Long alertNotificationId) {
		return findByCustomerIdAndId(customerId, alertNotificationId)
			.orElseThrow(() -> new DomainException(ALERT_NOTIFICATION_NOT_FOUND));
	}

	Optional<CustomerReservationNotification> findByCustomerIdAndId(Long customerId, Long id);
}
