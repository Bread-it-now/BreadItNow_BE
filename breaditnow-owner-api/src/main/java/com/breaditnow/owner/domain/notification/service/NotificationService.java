package com.breaditnow.owner.domain.notification.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import com.breaditnow.common.message.NotificationMessage;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationService {
	private final RabbitTemplate rabbitTemplate;

	public void sendNotification(Long productId, String message) {
		NotificationMessage notification = new NotificationMessage(productId, message);

		// Exchange: "notification.exchange"
		// RoutingKey: "notification.routingkey"
		// Payload: notification DTO
		rabbitTemplate.convertAndSend(
			"notification.exchange",
			"notification.routingkey",
			notification
		);

		log.info("[Publisher] 알림 메시지 발행: {}", notification);
	}
}
