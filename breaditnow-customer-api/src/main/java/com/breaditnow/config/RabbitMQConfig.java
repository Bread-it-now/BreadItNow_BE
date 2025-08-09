package com.breaditnow.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    public static final String EXCHANGE_NAME = "customer.events.exchange";
    public static final String PASSWORD_CHANGE_QUEUE_NAME = "auth.password-change.queue";
    public static final String PASSWORD_CHANGE_ROUTING_KEY = "customer.password.changed";

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }

    @Bean
    public Queue passwordChangeQueue() {
        return new Queue(PASSWORD_CHANGE_QUEUE_NAME);
    }

    @Bean
    public Binding binding(Queue passwordChangeQueue, TopicExchange exchange) {
        return BindingBuilder.bind(passwordChangeQueue)
                .to(exchange)
                .with(PASSWORD_CHANGE_ROUTING_KEY);
    }

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
