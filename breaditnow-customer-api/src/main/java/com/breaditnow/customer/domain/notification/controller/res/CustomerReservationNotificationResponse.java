package com.breaditnow.customer.domain.notification.controller.res;

import static com.breaditnow.domain.domain.notification.enumerate.NotificationType.*;
import static com.fasterxml.jackson.annotation.JsonFormat.Shape.*;

import java.time.LocalDateTime;
import java.util.List;

import com.breaditnow.domain.domain.notification.entity.CustomerReservationNotification;
import com.breaditnow.domain.domain.notification.enumerate.NotificationType;
import com.breaditnow.domain.domain.reservation.entity.ReservationProduct;
import com.breaditnow.domain.domain.reservation.enumerate.ReservationStatus;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Builder;

@Builder
public record CustomerReservationNotificationResponse(
	Long notificationId,
	Long bakeryId,
	String bakeryName,
	Integer productSize,
	NotificationType type,
	List<String> productsName,
	ReservationStatus reservationStatus,
	Boolean isRead,
	@JsonFormat(shape = STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
	LocalDateTime createdAt,
	@JsonFormat(shape = STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
	LocalDateTime pickupDeadline
) implements CustomerNotificationResponse {
	public static CustomerNotificationResponse of(CustomerReservationNotification reservationNotification) {
		return CustomerReservationNotificationResponse.builder()
			.notificationId(reservationNotification.getId())
			.bakeryId(reservationNotification.getReservation().getBakery().getId())
			.bakeryName(reservationNotification.getReservation().getBakery().getName())
			.productSize(reservationNotification.getReservation().getReservationProducts().size())
			.type(RESERVATION)
			.productsName(
				reservationNotification.getReservation().getReservationProducts()
					.stream()
					.map(ReservationProduct::getProductName)
					.toList()
			)
			.reservationStatus(reservationNotification.getReservation().getStatus())
			.isRead(reservationNotification.isRead())
			.createdAt(reservationNotification.getCreatedAt())
			.pickupDeadline(reservationNotification.getReservation().getPickupDeadline())
			.build();
	}
}
