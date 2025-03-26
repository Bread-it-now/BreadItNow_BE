package com.breaditnow.domain.domain.notification.repository;

import static com.breaditnow.domain.global.exception.DomainErrorCode.*;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.breaditnow.domain.domain.notification.entity.CustomerNotification;
import com.breaditnow.domain.global.exception.DomainException;

public interface UnifiedNotificationRepository
	extends JpaRepository<CustomerNotification, Long>, UnifiedNotificationRepositoryCustom {

	default CustomerNotification getByCustomerIdAndId(Long customerId, Long notificationId) {
		return findByCustomerIdAndId(customerId, notificationId)
			.orElseThrow(() -> new DomainException(NOTIFICATION_NOT_FOUND));
	}

	Optional<CustomerNotification> findByCustomerIdAndId(Long customerId, Long id);
}
