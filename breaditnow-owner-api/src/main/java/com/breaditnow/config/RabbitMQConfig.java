package com.breaditnow.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    public static final String RESERVATION_EVENT_EXCHANGE = "reservation.event.exchange";
    public static final String RESERVATION_CREATED_QUEUE = "q.reservation.created.for.owner";
    public static final String RESERVATION_CREATED_ROUTING_KEY = "routing.key.reservation.created";

    @Bean
    public MessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public Queue reservationCreatedQueue() {
        return new Queue(RESERVATION_CREATED_QUEUE, true);
    }

    @Bean
    public DirectExchange reservationEventExchange() {
        return new DirectExchange(RESERVATION_EVENT_EXCHANGE);
    }

    @Bean
    public Binding bindingReservationCreated(Queue reservationCreatedQueue, DirectExchange reservationEventExchange) {
        return BindingBuilder.bind(reservationCreatedQueue)
                .to(reservationEventExchange)
                .with(RESERVATION_CREATED_ROUTING_KEY);
    }
}