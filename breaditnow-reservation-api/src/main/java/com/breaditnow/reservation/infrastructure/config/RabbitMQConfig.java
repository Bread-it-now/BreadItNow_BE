package com.breaditnow.reservation.infrastructure.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    public static final String BAKERY_INFO_EXCHANGE = "bakery.info.exchange";
    public static final String BAKERY_INFO_QUEUE = "q.bakery.info.for.reservation";

    @Bean
    public FanoutExchange bakeryInfoExchange() {
        return new FanoutExchange(BAKERY_INFO_EXCHANGE);
    }

    @Bean
    public Queue bakeryInfoQueue() {
        return new Queue(BAKERY_INFO_QUEUE, true);
    }

    @Bean
    public Binding binding(Queue queue, FanoutExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange);
    }

    @Bean
    public MessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
