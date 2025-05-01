package com.breaditnow.external.domain.firebase.dto.request;

import static lombok.AccessLevel.*;

import java.util.List;

import com.google.firebase.messaging.MulticastMessage;
import com.google.firebase.messaging.Notification;

import lombok.Builder;
import lombok.NonNull;

@Builder(access = PRIVATE)
public record NotificationMulticastRequest(
	@NonNull List<String> targetTokens,
	String title,
	String body
) implements NotificationRequest {

	public static NotificationMulticastRequest of(List<String> tokens, String title, String body) {
		return NotificationMulticastRequest.builder()
			.targetTokens(tokens)
			.title(title)
			.body(body)
			.build();
	}

	public MulticastMessage.Builder buildSendMessage() {
		return MulticastMessage.builder()
			.setNotification(toNotification())
			.addAllTokens(targetTokens);
	}

	public Notification toNotification() {
		return Notification.builder()
			.setTitle(title)
			.setBody(body)
			.build();
	}
}
