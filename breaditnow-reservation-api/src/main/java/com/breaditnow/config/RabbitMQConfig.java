package com.breaditnow.config;


import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    // Reservation Events 상수
    public static final String RESERVATION_EVENT_EXCHANGE = "reservation.event.exchange";
    public static final String RESERVATION_CREATED_ROUTING_KEY = "routing.key.reservation.created";

    // Common Bean
    @Bean
    public MessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    // Reservation Events
    @Bean
    public DirectExchange reservationEventExchange() {
        return new DirectExchange(RESERVATION_EVENT_EXCHANGE);
    }
}