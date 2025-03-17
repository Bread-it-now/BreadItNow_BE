package com.breaditnow.owner.domain.notification.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import com.breaditnow.common.message.AlertNotificationMessage;
import com.breaditnow.owner.domain.notification.controller.req.NotificationRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationService {
	private final RabbitTemplate rabbitTemplate;

	public void sendNotification(Long ownerId, NotificationRequest notificationRequest) {
		AlertNotificationMessage alertNotificationMessage = notificationRequest.toAlertNotificationMessage();

		// Exchange: "notification.exchange"
		// RoutingKey: "notification.routingkey"
		// Payload: notification DTO
		rabbitTemplate.convertAndSend(
			"notification.exchange",
			"notification.routingkey",
			alertNotificationMessage
		);

		log.info("[Publisher] 알림 메시지 발행: {}", alertNotificationMessage);
	}
}
