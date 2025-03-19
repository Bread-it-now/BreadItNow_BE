package com.breaditnow.domain.domain.notification.enumerate;

import static com.breaditnow.domain.global.exception.DomainErrorCode.*;

import java.util.Arrays;
import java.util.List;

import org.h2.util.StringUtils;

import com.breaditnow.domain.global.exception.DomainException;

public enum NotificationType {
	ALERT, RESERVATION;

	public static List<NotificationType> parseTypes(String type) {
		if (StringUtils.isNullOrEmpty(type)) {
			throw new DomainException(INVALID_NOTIFICATION_TYPE);
		}

		String normalized = type.trim().toUpperCase();
		if ("ALL".equals(normalized)) {
			return Arrays.asList(ALERT, RESERVATION);
		}
		try {
			NotificationType nt = NotificationType.valueOf(normalized);
			return List.of(nt);
		} catch (IllegalArgumentException e) {
			throw new DomainException(INVALID_NOTIFICATION_TYPE);
		}
	}
}
