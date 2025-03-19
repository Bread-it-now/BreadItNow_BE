package com.breaditnow.customer.domain.notification.controller.res;

import java.time.LocalDateTime;
import java.util.List;

import com.breaditnow.domain.domain.notification.entity.CustomerAlertNotification;
import com.breaditnow.domain.domain.notification.entity.CustomerReservationNotification;
import com.breaditnow.domain.domain.reservation.entity.ReservationProduct;
import com.breaditnow.domain.domain.reservation.enumerate.ReservationStatus;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class NotificationResponse {
	// alert 알림용 필드
	private Long alertNotificationId;
	// reservation 알림용 필드
	private Long reservationNotificationId;

	// 공통 필드
	private String type; // "alert" 또는 "reservation"
	private Long bakeryId;
	private boolean isRead;
	private LocalDateTime createdAt;

	// alert 전용
	private Long productId;
	private Integer remainingCount;
	private Integer alertCount;

	// reservation 전용
	private Integer productSize;
	private List<String> productsName;
	private ReservationStatus reservationStatus;

	// 팩토리 메서드: alert 알림 생성
	public static NotificationResponse fromAlert(CustomerAlertNotification alert) {
		NotificationResponse dto = new NotificationResponse();
		dto.alertNotificationId = alert.getId();
		dto.type = "alert";
		dto.bakeryId = alert.getProduct().getBakery().getId();
		dto.productId = alert.getProduct().getId();
		dto.remainingCount = alert.getRemainingCount();
		dto.alertCount = alert.getAlertCount();
		dto.isRead = alert.isRead();
		dto.createdAt = alert.getCreatedAt();
		return dto;
	}

	// 팩토리 메서드: reservation 알림 생성
	public static NotificationResponse fromReservation(CustomerReservationNotification reservationNotification) {
		NotificationResponse dto = new NotificationResponse();
		dto.reservationNotificationId = reservationNotification.getId();
		dto.type = "reservation";
		dto.bakeryId = reservationNotification.getReservation().getBakery().getId();
		dto.productSize = reservationNotification.getReservation().getReservationProducts().size();
		dto.productsName = reservationNotification.getReservation().getReservationProducts().stream()
			.map(ReservationProduct::getProductName)
			.toList();
		dto.reservationStatus = reservationNotification.getReservation().getStatus();
		dto.isRead = reservationNotification.isRead();
		dto.createdAt = reservationNotification.getCreatedAt();
		return dto;
	}
	
}

