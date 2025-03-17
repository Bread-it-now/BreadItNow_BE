package com.breaditnow.common.message;

import java.io.Serializable;

public record NotificationMessage(
	Long productId,
	String alertMessage
) implements Serializable {
}
