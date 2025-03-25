package com.breaditnow.domain.domain.notification.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.breaditnow.domain.domain.notification.entity.OwnerReservationNotification;

public interface OwnerReservationNotificationRepository extends JpaRepository<OwnerReservationNotification, Long> {
	@Query("SELECT n FROM OwnerReservationNotification n JOIN n.reservation r " +
		"WHERE n.owner.id = :ownerId")
	Page<OwnerReservationNotification> findByReservationStatus(
		@Param("ownerId") Long ownerId,
		Pageable pageable);
}


