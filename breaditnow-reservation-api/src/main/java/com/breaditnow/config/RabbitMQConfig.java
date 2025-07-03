package com.breaditnow.config;

import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    public static final String BREADITNOW_EXCHANGE = "breaditnow.exchange";

    public static final String RESERVATION_CREATED_ROUTING_KEY = "reservation.created";
    public static final String RESERVATION_APPROVED_ROUTING_KEY = "reservation.approved";
    public static final String RESERVATION_PARTIALLY_APPROVED_ROUTING_KEY = "reservation.partially_approved";
    public static final String RESERVATION_CANCELLED_ROUTING_KEY = "reservation.canceled";

    @Bean
    public MessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public TopicExchange breaditnowExchange() {
        return new TopicExchange(BREADITNOW_EXCHANGE);
    }
}