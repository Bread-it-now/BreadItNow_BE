package com.breaditnow.customer.domain.notification;

import java.util.List;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.breaditnow.common.message.AlertNotificationMessage;
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

	@Transactional
	@RabbitListener(queues = "notification.queue")
	public void receiveNotification(AlertNotificationMessage alertNotificationMessage) {
		System.out.println("[Consumer] 알림 메시지 수신: " + alertNotificationMessage);

		// product와 bakery가 맞는지 확인
		productRepository.getByBakeryIdAndId(alertNotificationMessage.bakeryId(),
			alertNotificationMessage.productId());

		// alertRepository에서 해당 product을 알람 받겠다고 한 고객들 정보인 List<Customer>을 받아오기(isAlert = true인 Customer만 받아오기)
		List<Customer> customers = alertRepository.findByProductIdAndIsActiveTrue(alertNotificationMessage.productId());
		Product product = productRepository.getById(alertNotificationMessage.productId());

		// List<Customer>에 해당하는 고객들을 CustomerAlertNotification에 저장하기
		for (Customer customer : customers) {
			CustomerAlertNotification customerAlertNotification = CustomerAlertNotification.builder()
				.customer(customer)
				.bakeryName(product.getBakery().getName())
				.product(product)
				.productName(product.getName())
				.remainingCount(product.getStock())
				.alertCount(customers.size())
				.build();
			notificationRepository.save(customerAlertNotification);
		}

		// FCM에 알림 전달하기(실제 푸시 로직을 PushService에 위임)
	}
}
