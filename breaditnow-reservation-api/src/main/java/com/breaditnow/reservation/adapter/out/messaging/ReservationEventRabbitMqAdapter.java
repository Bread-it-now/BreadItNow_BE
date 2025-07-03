package com.breaditnow.reservation.adapter.out.messaging;

import com.breaditnow.common.event.StockDecreaseRequestedEvent;
import com.breaditnow.config.RabbitMQConfig;
import com.breaditnow.reservation.application.event.ReservationStatusChangedEvent;
import com.breaditnow.reservation.domain.port.out.ReservationEventPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ReservationEventRabbitMqAdapter implements ReservationEventPort {
    private final RabbitTemplate rabbitTemplate;

    @Override
    public void publish(ReservationStatusChangedEvent event) {
        String routingKey = "reservation.status." + event.reservationStatus().name().toLowerCase();
        log.info("Publishing event with key [{}]: {}", routingKey, event);
        rabbitTemplate.convertAndSend(RabbitMQConfig.BREADITNOW_EXCHANGE, routingKey, event);
    }

    @Override
    public void publishStockDecreaseRequest(StockDecreaseRequestedEvent event) {
        String routingKey = "v1.stock.decrease.requested";
        log.info("Publishing stock decrease request event with key [{}]: {}", routingKey, event);
        rabbitTemplate.convertAndSend(RabbitMQConfig.BREADITNOW_EXCHANGE, routingKey, event);
    }
}
