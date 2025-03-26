package com.breaditnow.common.message;

import java.io.Serializable;

public record AlertNotificationMessage(
	Long bakeryId,
	Long productId
) implements Serializable {
}
