package com.breaditnow.reservation.infrastructure.config;


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
    // Bakery Events 상수
    public static final String BAKERY_EVENT_EXCHANGE = "bakery.event.exchange";
    public static final String BAKERY_CREATED_QUEUE = "q.bakery.created.for.reservation";
    public static final String BAKERY_STATUS_CHANGED_QUEUE = "q.bakery.status.changed.for.reservation";
    public static final String BAKERY_DELETED_QUEUE = "q.bakery.deleted.for.reservation";

    public static final String BAKERY_CREATED_ROUTING_KEY = "routing.key.bakery.created";
    public static final String BAKERY_STATUS_CHANGED_ROUTING_KEY = "routing.key.bakery.status.changed";
    public static final String BAKERY_DELETED_ROUTING_KEY = "routing.key.bakery.deleted";

    // Product Events 상수
    public static final String PRODUCT_EVENT_EXCHANGE = "product.event.exchange";
    public static final String PRODUCT_CREATED_QUEUE = "q.product.created.for.reservation";
    public static final String PRODUCT_UPDATED_QUEUE = "q.product.updated.for.reservation";
    public static final String PRODUCT_DELETED_QUEUE = "q.product.deleted.for.reservation";

    public static final String PRODUCT_CREATED_ROUTING_KEY = "routing.key.product.created";
    public static final String PRODUCT_UPDATED_ROUTING_KEY = "routing.key.product.updated";
    public static final String PRODUCT_DELETED_ROUTING_KEY = "routing.key.product.deleted";

    // Common Bean
    @Bean
    public MessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    // Bakery Events
    @Bean
    public DirectExchange bakeryEventExchange() {
        return new DirectExchange(BAKERY_EVENT_EXCHANGE);
    }

    @Bean public Queue bakeryCreatedQueue() { return new Queue(BAKERY_CREATED_QUEUE, true); }
    @Bean public Queue bakeryStatusChangedQueue() { return new Queue(BAKERY_STATUS_CHANGED_QUEUE, true); }
    @Bean public Queue bakeryDeletedQueue() { return new Queue(BAKERY_DELETED_QUEUE, true); }

    @Bean
    public Binding bindingBakeryCreated(Queue bakeryCreatedQueue, DirectExchange bakeryEventExchange) {
        return BindingBuilder.bind(bakeryCreatedQueue).to(bakeryEventExchange).with(BAKERY_CREATED_ROUTING_KEY);
    }
    @Bean
    public Binding bindingBakeryStatusChanged(Queue bakeryStatusChangedQueue, DirectExchange bakeryEventExchange) {
        return BindingBuilder.bind(bakeryStatusChangedQueue).to(bakeryEventExchange).with(BAKERY_STATUS_CHANGED_ROUTING_KEY);
    }
    @Bean
    public Binding bindingBakeryDeleted(Queue bakeryDeletedQueue, DirectExchange bakeryEventExchange) {
        return BindingBuilder.bind(bakeryDeletedQueue).to(bakeryEventExchange).with(BAKERY_DELETED_ROUTING_KEY);
    }

    // Product Events
    @Bean
    public DirectExchange productEventExchange() {
        return new DirectExchange(PRODUCT_EVENT_EXCHANGE);
    }

    @Bean public Queue productCreatedQueue() { return new Queue(PRODUCT_CREATED_QUEUE, true); }
    @Bean public Queue productUpdatedQueue() { return new Queue(PRODUCT_UPDATED_QUEUE, true); }
    @Bean public Queue productDeletedQueue() { return new Queue(PRODUCT_DELETED_QUEUE, true); }

    @Bean
    public Binding bindingProductCreated(Queue productCreatedQueue, DirectExchange productEventExchange) {
        return BindingBuilder.bind(productCreatedQueue).to(productEventExchange).with(PRODUCT_CREATED_ROUTING_KEY);
    }
    @Bean
    public Binding bindingProductUpdated(Queue productUpdatedQueue, DirectExchange productEventExchange) {
        return BindingBuilder.bind(productUpdatedQueue).to(productEventExchange).with(PRODUCT_UPDATED_ROUTING_KEY);
    }
    @Bean
    public Binding bindingProductDeleted(Queue productDeletedQueue, DirectExchange productEventExchange) {
        return BindingBuilder.bind(productDeletedQueue).to(productEventExchange).with(PRODUCT_DELETED_ROUTING_KEY);
    }
}