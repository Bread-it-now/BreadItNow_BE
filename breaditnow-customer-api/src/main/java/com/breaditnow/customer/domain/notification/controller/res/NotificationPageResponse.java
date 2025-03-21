package com.breaditnow.customer.domain.notification.controller.res;

import java.util.List;

import org.springframework.data.domain.Page;

import com.breaditnow.common.page.PageInfo;
import com.breaditnow.domain.domain.notification.entity.CustomerNotification;

import lombok.Builder;

@Builder
public record NotificationPageResponse(
	List<CustomerNotificationResponse> notifications,
	PageInfo pageInfo
) {
	public static NotificationPageResponse of(Page<CustomerNotification> customerNotificationPage) {
		List<CustomerNotificationResponse> notifications = customerNotificationPage.stream()
			.map(CustomerNotificationResponseFactory::of)
			.toList();

		PageInfo pageInfo = PageInfo.builder()
			.totalElements(customerNotificationPage.getTotalElements())
			.totalPages(customerNotificationPage.getTotalPages())
			.isLast(customerNotificationPage.isLast())
			.currPage(customerNotificationPage.getPageable().getPageNumber())
			.build();

		return NotificationPageResponse.builder()
			.notifications(notifications)
			.pageInfo(pageInfo)
			.build();
	}
}
