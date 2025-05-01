package com.breaditnow.owner.domain.notification.controller.res;

import java.util.List;

import org.springframework.data.domain.Page;

import com.breaditnow.common.page.PageInfo;
import com.breaditnow.domain.domain.notification.entity.OwnerReservationNotification;

import lombok.Builder;

@Builder
public record NotificationPageResponse(
	List<OwnerReservationNotificationResponse> notifications,
	PageInfo pageInfo
) {
	public static NotificationPageResponse of(Page<OwnerReservationNotification> ownerReservationNotificationPage) {
		PageInfo pageInfo = PageInfo.builder()
			.totalElements(ownerReservationNotificationPage.getTotalElements())
			.totalPages(ownerReservationNotificationPage.getTotalPages())
			.isLast(ownerReservationNotificationPage.isLast())
			.currPage(ownerReservationNotificationPage.getPageable().getPageNumber())
			.build();

		List<OwnerReservationNotificationResponse> notifications = ownerReservationNotificationPage.getContent()
			.stream()
			.map(OwnerReservationNotificationResponse::of)
			.toList();

		return NotificationPageResponse.builder()
			.notifications(notifications)
			.pageInfo(pageInfo)
			.build();

	}
}
