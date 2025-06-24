package com.breaditnow.owner.common.config;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    public static final String BAKERY_EVENT_EXCHANGE = "bakery.event.exchange";
    public static final String CREATE_ROUTING_KEY = "routing.key.bakery.created";
    public static final String STATUS_UPDATE_ROUTING_KEY = "routing.key.bakery.status.changed";
    public static final String DELETE_ROUTING_KEY = "routing.key.bakery.deleted";

    public static final String PRODUCT_EVENT_EXCHANGE = "product.event.exchange";
    public static final String PRODUCT_CREATED_ROUTING_KEY = "routing.key.product.created";
    public static final String PRODUCT_UPDATED_ROUTING_KEY = "routing.key.product.updated";
    public static final String PRODUCT_DELETED_ROUTING_KEY = "routing.key.product.deleted";

    @Bean
    public MessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public DirectExchange bakeryEventExchange() {
        return new DirectExchange(BAKERY_EVENT_EXCHANGE);
    }

    @Bean
    public DirectExchange productEventExchange() {
        return new DirectExchange(PRODUCT_EVENT_EXCHANGE);
    }
}