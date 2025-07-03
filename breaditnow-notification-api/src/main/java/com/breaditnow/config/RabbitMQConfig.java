package com.breaditnow.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String BREADITNOW_EXCHANGE = "breaditnow.exchange";
    public static final String NOTIFICATION_RESERVATION_QUEUE = "notification.reservation.queue";
    public static final String RESERVATION_ROUTING_PATTERN = "reservation.#";

    @Bean
    public MessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public TopicExchange breaditnowExchange() {
        return new TopicExchange(BREADITNOW_EXCHANGE);
    }

    @Bean
    public Queue notificationReservationQueue() {
        return new Queue(NOTIFICATION_RESERVATION_QUEUE, true);
    }

    @Bean
    public Binding binding(Queue notificationReservationQueue, TopicExchange breaditnowExchange) {
        return BindingBuilder.bind(notificationReservationQueue)
                .to(breaditnowExchange)
                .with(RESERVATION_ROUTING_PATTERN);
    }
}