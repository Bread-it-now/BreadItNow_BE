package com.breaditnow.customer.customer.adapter.out.event;

import com.breaditnow.customer.customer.domain.port.out.PublishEventPort;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import static com.breaditnow.customer.config.RabbitMQConfig.EXCHANGE_NAME;

@Component
@RequiredArgsConstructor
public class RabbitMqEventPublisher implements PublishEventPort {
    private final RabbitTemplate rabbitTemplate;

    @Override
    public void publish(Object event, String routingKey) {
        rabbitTemplate.convertAndSend(EXCHANGE_NAME, routingKey, event);
    }
}
