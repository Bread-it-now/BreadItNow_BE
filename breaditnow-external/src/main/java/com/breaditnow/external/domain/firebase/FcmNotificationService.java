package com.breaditnow.external.domain.firebase;

import org.springframework.stereotype.Service;

import com.breaditnow.external.domain.firebase.dto.request.NotificationMulticastRequest;
import com.breaditnow.external.domain.firebase.dto.request.NotificationRequest;
import com.breaditnow.external.domain.firebase.dto.request.NotificationSingleRequest;
import com.google.firebase.messaging.ApnsConfig;
import com.google.firebase.messaging.Aps;
import com.google.firebase.messaging.ApsAlert;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.MulticastMessage;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class FcmNotificationService {
	private final FirebaseMessaging firebaseMessaging;

	public void sendMessage(NotificationSingleRequest request) {
		Message message = request.buildMessage().setApnsConfig(getApnsConfig(request)).build();
		firebaseMessaging.sendAsync(message);
	}

	public void sendMessages(NotificationMulticastRequest request) {
		if (request.targetTokens().isEmpty()) {
			return;
		}
		MulticastMessage messages = request.buildSendMessage().setApnsConfig(getApnsConfig(request)).build();
		firebaseMessaging.sendEachForMulticastAsync(messages);
	}

	private ApnsConfig getApnsConfig(NotificationRequest request) {
		ApsAlert alert = ApsAlert.builder().setTitle(request.title()).setBody(request.body()).build();
		Aps aps = Aps.builder().setAlert(alert).setSound("default").build();
		return ApnsConfig.builder().setAps(aps).build();
	}
}
