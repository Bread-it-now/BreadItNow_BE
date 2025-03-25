package com.breaditnow.owner.domain.notification.controller.res;

import java.time.LocalDateTime;
import java.util.List;

import com.breaditnow.domain.domain.notification.entity.OwnerReservationNotification;
import com.breaditnow.domain.domain.reservation.entity.Reservation;
import com.breaditnow.domain.domain.reservation.entity.ReservationProduct;
import com.breaditnow.domain.domain.reservation.enumerate.ReservationStatus;

import lombok.Builder;

@Builder
public record OwnerReservationNotificationResponse(
	Long reservationId,
	String nickname,
	ReservationStatus status,
	boolean isRead,
	List<String> productsName,
	LocalDateTime createAt
) {
	public static OwnerReservationNotificationResponse of(OwnerReservationNotification notification) {
		Reservation reservation = notification.getReservation();

		String nickname = reservation.getCustomer().getNickname();
		ReservationStatus status = reservation.getStatus();
		List<String> productsName = reservation.getReservationProducts().stream()
			.map(ReservationProduct::getProductName)
			.toList();

		return OwnerReservationNotificationResponse.builder()
			.reservationId(reservation.getId())
			.nickname(nickname)
			.productsName(productsName)
			.status(status)
			.isRead(notification.isRead())
			.createAt(notification.getCreatedAt())
			.build();
	}
}
