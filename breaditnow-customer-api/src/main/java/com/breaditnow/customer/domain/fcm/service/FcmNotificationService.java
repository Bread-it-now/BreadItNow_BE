package com.breaditnow.customer.domain.fcm.service;

import static com.breaditnow.customer.global.exception.CustomerErrorCode.*;

import org.springframework.stereotype.Service;

import com.breaditnow.customer.global.exception.CustomerException;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;

@Service
public class FcmNotificationService {
	public String sendPushNotification(String fcmToken) {
		Message message = Message.builder()
			.putData("title", "test title")
			.putData("body", "body")
			.setToken(fcmToken)
			.build();

		try {
			return FirebaseMessaging.getInstance().send(message);
		} catch (FirebaseMessagingException e) {
			throw new CustomerException(FCM_SEND_FAILED);
		}
	}
}
