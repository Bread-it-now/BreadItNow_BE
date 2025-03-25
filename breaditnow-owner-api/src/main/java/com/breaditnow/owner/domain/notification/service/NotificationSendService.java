package com.breaditnow.owner.domain.notification.service;

import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.breaditnow.domain.domain.alert.repository.CustomerProductAlertRepository;
import com.breaditnow.domain.domain.bakery.repository.BakeryRepository;
import com.breaditnow.domain.domain.customer.entity.Customer;
import com.breaditnow.domain.domain.notification.entity.CustomerAlertNotification;
import com.breaditnow.domain.domain.notification.repository.UnifiedNotificationRepository;
import com.breaditnow.domain.domain.product.entity.Product;
import com.breaditnow.domain.domain.product.repository.ProductRepository;
import com.breaditnow.external.firebase.FcmNotificationService;
import com.breaditnow.external.firebase.dto.request.NotificationMulticastRequest;
import com.breaditnow.owner.domain.notification.controller.req.NotificationRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NotificationSendService {
	private final BakeryRepository bakeryRepository;
	private final ProductRepository productRepository;
	private final CustomerProductAlertRepository alertRepository;
	private final UnifiedNotificationRepository notificationRepository;
	private final FcmNotificationService fcmNotificationService;

	@Transactional
	public void sendNotification(Long ownerId, NotificationRequest request) {
		verifyOwnerForBakery(ownerId, request.bakeryId());

		Product product = productRepository.getByBakeryIdAndId(request.bakeryId(), request.productId());
		List<Customer> customers = alertRepository.findByProductIdAndIsActiveTrueAndFcmTokenExists(request.productId());

		int alertCount = customers.size();
		customers.forEach(customer -> saveCustomerAlertNotification(customer, product, alertCount));

		List<String> fcmTokens = extractFcmTokens(customers);
		sendFcmNotifications(fcmTokens);
	}

	private void verifyOwnerForBakery(Long ownerId, Long bakeryId) {
		bakeryRepository.getByOwnerIdAndId(ownerId, bakeryId);
	}

	private void saveCustomerAlertNotification(Customer customer, Product product, int alertCount) {
		CustomerAlertNotification notification = CustomerAlertNotification.builder()
			.customer(customer)
			.bakeryName(product.getBakery().getName())
			.product(product)
			.productName(product.getName())
			.remainingCount(product.getStock())
			.alertCount(alertCount)
			.build();
		notificationRepository.save(notification);
	}

	private List<String> extractFcmTokens(List<Customer> customers) {
		return customers.stream()
			.map(Customer::getFcmToken)
			.filter(Objects::nonNull)
			.toList();
	}

	private void sendFcmNotifications(List<String> fcmTokens) {
		String title = "test title";
		String body = "test body";
		NotificationMulticastRequest multicastRequest = NotificationMulticastRequest.of(fcmTokens, title, body);
		fcmNotificationService.sendMessages(multicastRequest);
	}

}
