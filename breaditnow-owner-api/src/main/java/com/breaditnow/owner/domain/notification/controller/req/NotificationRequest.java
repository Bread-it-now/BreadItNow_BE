package com.breaditnow.owner.domain.notification.controller.req;

import com.breaditnow.common.message.AlertNotificationMessage;

public record NotificationRequest(
	Long bakeryId,
	Long productId
) {
	public AlertNotificationMessage toAlertNotificationMessage() {
		return new AlertNotificationMessage(bakeryId, productId);
	}
}
