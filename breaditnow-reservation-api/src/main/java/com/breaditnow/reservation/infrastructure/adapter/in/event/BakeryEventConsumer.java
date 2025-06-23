package com.breaditnow.reservation.infrastructure.adapter.in.event;

import com.breaditnow.reservation.application.dto.event.BakeryInfoUpdatedEvent;
import com.breaditnow.reservation.infrastructure.config.RabbitMQConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class BakeryEventConsumer {

    @RabbitListener(queues = RabbitMQConfig.BAKERY_INFO_QUEUE)
    public void receiveBakeryUpdate(BakeryInfoUpdatedEvent event) {
        log.info("★★★ RabbitMQ 메시지 수신 성공! ★★★");
        log.info("수신된 빵집 ID: {}", event.bakeryId());
        log.info("수신된 빵집 이름: {}", event.name());
        log.info("수신된 운영 상태: {}", event.operatingStatus());
        log.info("수신된 전체 이벤트 내용: {}", event);
    }
}
