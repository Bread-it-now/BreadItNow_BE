package com.breaditnow.customer.domain.notification;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.breaditnow.common.message.MessageDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class NotificationListener {

	@RabbitListener(queues = "${rabbitmq.queue.name}")
	public void handleNotification(MessageDto message) {
		log.info("Received message: {}", message);
		// 수신된 메시지를 FCM 푸시 알림으로 전송
	}
}
