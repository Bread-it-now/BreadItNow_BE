package com.breaditnow.customer.notification.controller.res;

import com.breaditnow.domain.domain.notification.entity.CustomerAlertNotification;
import com.breaditnow.domain.domain.notification.entity.CustomerNotification;
import com.breaditnow.domain.domain.notification.entity.CustomerReservationNotification;

public class CustomerNotificationResponseFactory {
	public static CustomerNotificationResponse of(CustomerNotification notification) {
		if (notification instanceof CustomerAlertNotification alertNotification) {
			return CustomerAlertNotificationResponse.of(alertNotification);
		} else if (notification instanceof CustomerReservationNotification reservationNotification) {
			return CustomerReservationNotificationResponse.of(reservationNotification);
		}
		return null;
	}
}
