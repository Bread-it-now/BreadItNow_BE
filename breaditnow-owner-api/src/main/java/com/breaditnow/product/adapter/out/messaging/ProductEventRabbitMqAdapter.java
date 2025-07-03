package com.breaditnow.product.adapter.out.messaging;

import com.breaditnow.common.event.StockUpdateResultEvent;
import com.breaditnow.config.RabbitMQConfig;
import com.breaditnow.product.domain.port.out.ProductEventPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProductEventRabbitMqAdapter implements ProductEventPort {
    private final RabbitTemplate rabbitTemplate;

    @Override
    public void publishStockUpdateResult(StockUpdateResultEvent event) {
        String routingKey = switch (event.status()) {
            case SUCCESS -> "v1.stock.update.succeeded";
            case FAILURE -> "v1.stock.update.failed";
        };

        log.info("재고 처리 결과 이벤트를 발행합니다. Exchange: {}, RoutingKey: {}, 예약 ID: {}",
                RabbitMQConfig.EXCHANGE_NAME, routingKey, event.reservationId());

        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, routingKey, event);
    }
}
