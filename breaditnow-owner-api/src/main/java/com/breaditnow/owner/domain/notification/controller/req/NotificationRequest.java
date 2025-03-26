package com.breaditnow.owner.domain.notification.controller.req;

import com.breaditnow.common.message.AlertNotificationMessage;

import jakarta.validation.constraints.NotNull;

public record NotificationRequest(
	@NotNull Long bakeryId,
	@NotNull Long productId
) {
	public AlertNotificationMessage toAlertNotificationMessage() {
		return new AlertNotificationMessage(bakeryId, productId);
	}
}
