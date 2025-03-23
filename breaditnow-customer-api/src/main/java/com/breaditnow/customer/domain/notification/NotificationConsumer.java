package com.breaditnow.customer.domain.notification;

import java.util.List;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.breaditnow.common.message.AlertNotificationMessage;
import com.breaditnow.customer.domain.fcm.service.FcmNotificationService;
import com.breaditnow.domain.domain.alert.repository.CustomerProductAlertRepository;
import com.breaditnow.domain.domain.customer.entity.Customer;
import com.breaditnow.domain.domain.notification.entity.CustomerAlertNotification;
import com.breaditnow.domain.domain.notification.repository.UnifiedNotificationRepository;
import com.breaditnow.domain.domain.product.entity.Product;
import com.breaditnow.domain.domain.product.repository.ProductRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NotificationConsumer {
	private final CustomerProductAlertRepository alertRepository;
	private final UnifiedNotificationRepository notificationRepository;
	private final ProductRepository productRepository;
	private final FcmNotificationService fcmNotificationService;

	@Transactional
	@RabbitListener(queues = "notification.queue")
	public void receiveNotification(AlertNotificationMessage alertNotificationMessage) {
		log.info("[Consumer] 알림 메시지 수신: {} ", alertNotificationMessage);

		// product와 bakery가 맞는지 확인
		productRepository.getByBakeryIdAndId(alertNotificationMessage.bakeryId(),
			alertNotificationMessage.productId());

		List<Customer> customers = alertRepository.findByProductIdAndIsActiveTrue(alertNotificationMessage.productId());
		Product product = productRepository.getById(alertNotificationMessage.productId());

		for (Customer customer : customers) {
			saveCustomerAlertNotification(customer, product, customers.size());
			fcmNotificationService.sendPushNotification(customer.getFcmToken());
		}
	}

	private void saveCustomerAlertNotification(Customer customer, Product product, int alertCount) {
		CustomerAlertNotification customerAlertNotification = CustomerAlertNotification.builder()
			.customer(customer)
			.bakeryName(product.getBakery().getName())
			.product(product)
			.productName(product.getName())
			.remainingCount(product.getStock())
			.alertCount(alertCount)
			.build();
		notificationRepository.save(customerAlertNotification);
	}
}
