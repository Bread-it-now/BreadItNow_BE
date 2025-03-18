package com.breaditnow.owner.domain.notification.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import com.breaditnow.common.message.AlertNotificationMessage;
import com.breaditnow.domain.domain.bakery.repository.BakeryRepository;
import com.breaditnow.domain.domain.product.repository.ProductRepository;
import com.breaditnow.owner.domain.notification.controller.req.NotificationRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationService {
	private final BakeryRepository bakeryRepository;
	private final ProductRepository productRepository;
	private final RabbitTemplate rabbitTemplate;

	public void sendNotification(Long ownerId, NotificationRequest notificationRequest) {
		bakeryRepository.getByOwnerIdAndId(ownerId, notificationRequest.bakeryId()); // 요청하는 사업자가 해당 빵집 주인인지 확인해주는 로직
		productRepository.getByBakeryIdAndId(notificationRequest.bakeryId(),
			notificationRequest.productId()); // product와 bakery가 맞는지 확인

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
