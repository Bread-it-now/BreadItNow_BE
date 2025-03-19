package com.breaditnow.customer.domain.notification.controller.res;

import java.util.List;

import com.breaditnow.common.page.PageInfo;

public record NotificationPageResponse(
	List<?> notifications,
	PageInfo pageInfo
) {
}
