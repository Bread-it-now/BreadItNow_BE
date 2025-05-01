package com.breaditnow.owner.domain.notification.controller.req;

import com.breaditnow.common.message.AlertNotificationMessage;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record NotificationRequest(
	@NotNull
	@Schema(description = "알림을 위한 빵집 ID", example = "1001")
	Long bakeryId,

	@NotNull
	@Schema(description = "알림을 위한 상품 ID", example = "2002")
	Long productId
) {
	public AlertNotificationMessage toAlertNotificationMessage() {
		return new AlertNotificationMessage(bakeryId, productId);
	}
}
