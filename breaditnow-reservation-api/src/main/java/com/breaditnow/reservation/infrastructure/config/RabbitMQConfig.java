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
    public static final String BAKERY_EVENT_EXCHANGE = "bakery.event.exchange";

    public static final String CREATE_QUEUE = "q.bakery.created.for.reservation";
    public static final String STATUS_UPDATE_QUEUE = "q.bakery.status.changed.for.reservation";
    public static final String DELETE_QUEUE = "q.bakery.deleted.for.reservation";

    public static final String CREATE_ROUTING_KEY = "routing.key.bakery.created";
    public static final String STATUS_UPDATE_ROUTING_KEY = "routing.key.bakery.status.changed";
    public static final String DELETE_ROUTING_KEY = "routing.key.bakery.deleted";

    @Bean public DirectExchange bakeryEventExchange() {return new DirectExchange(BAKERY_EVENT_EXCHANGE);}
    @Bean public Queue createQueue() { return new Queue(CREATE_QUEUE, true); }
    @Bean public Queue statusUpdateQueue() { return new Queue(STATUS_UPDATE_QUEUE, true); }
    @Bean public Queue deleteQueue() { return new Queue(DELETE_QUEUE, true); }

    @Bean
    public Binding bindingCreate(Queue createQueue, DirectExchange exchange) {
        return BindingBuilder.bind(createQueue).to(exchange).with(CREATE_ROUTING_KEY);
    }

    @Bean
    public Binding bindingStatusUpdate(Queue statusUpdateQueue, DirectExchange exchange) {
        return BindingBuilder.bind(statusUpdateQueue).to(exchange).with(STATUS_UPDATE_ROUTING_KEY);
    }

    @Bean
    public Binding bindingDelete(Queue deleteQueue, DirectExchange exchange) {
        return BindingBuilder.bind(deleteQueue).to(exchange).with(DELETE_ROUTING_KEY);
    }

    @Bean
    public MessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}