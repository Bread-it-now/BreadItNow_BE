package com.breaditnow.customer.domain.notification;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import com.breaditnow.common.message.NotificationMessage;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class NotificationConsumer {
	/**
	 * "notification.queue" 큐에서 메시지를 꺼내 처리하는 메서드
	 */
	@RabbitListener(queues = "notification.queue")
	public void receiveNotification(NotificationMessage notification) {
		System.out.println("[Consumer] 알림 메시지 수신: " + notification);
		// 실제 푸시 로직을 PushService에 위임
	}
}
