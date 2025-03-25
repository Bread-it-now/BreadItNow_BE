package com.breaditnow.domain.domain.notification.repository;

import static com.breaditnow.domain.global.exception.DomainErrorCode.*;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.breaditnow.domain.domain.notification.entity.OwnerReservationNotification;
import com.breaditnow.domain.global.exception.DomainException;

public interface OwnerReservationNotificationRepository extends JpaRepository<OwnerReservationNotification, Long> {
	@Query("SELECT n FROM OwnerReservationNotification n JOIN n.reservation r " +
		"WHERE n.owner.id = :ownerId AND n.isActive = true")
	Page<OwnerReservationNotification> getNotifications(
		@Param("ownerId") Long ownerId,
		Pageable pageable);

	default OwnerReservationNotification getByOwnerIdAndId(Long ownerId, Long notificationId) {
		return findByOwnerIdAndId(ownerId, notificationId)
			.orElseThrow(() -> new DomainException(ALERT_NOTIFICATION_NOT_FOUND));
	}

	Optional<OwnerReservationNotification> findByOwnerIdAndId(Long ownerId, Long id);
}


