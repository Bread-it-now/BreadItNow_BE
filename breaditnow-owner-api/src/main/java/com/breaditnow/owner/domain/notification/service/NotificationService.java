package com.breaditnow.owner.domain.notification.service;

import static com.breaditnow.owner.global.config.RabbitMQConfig.*;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.breaditnow.common.message.AlertNotificationMessage;
import com.breaditnow.domain.domain.bakery.repository.BakeryRepository;
import com.breaditnow.domain.domain.notification.repository.UnifiedNotificationRepository;
import com.breaditnow.domain.domain.product.repository.ProductRepository;
import com.breaditnow.owner.domain.notification.controller.req.NotificationRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NotificationService {
	private final BakeryRepository bakeryRepository;
	private final ProductRepository productRepository;
	private final UnifiedNotificationRepository unifiedNotificationRepository;
	private final RabbitTemplate rabbitTemplate;

	@Transactional
	public void sendNotification(Long ownerId, NotificationRequest notificationRequest) {
		// 요청하는 사업자가 해당 빵집 주인인지 확인한다.
		bakeryRepository.getByOwnerIdAndId(ownerId, notificationRequest.bakeryId());

		// 해당 product와 bakery에 소속된 product인지 확인한다.
		productRepository.getByBakeryIdAndId(notificationRequest.bakeryId(),
			notificationRequest.productId());

		AlertNotificationMessage alertNotificationMessage = notificationRequest.toAlertNotificationMessage();

		rabbitTemplate.convertAndSend(EXCHANGE, ROUTING_KEY, alertNotificationMessage);

		log.info("[Publisher] 알림 메시지 발행: {}", alertNotificationMessage);
	}

}
