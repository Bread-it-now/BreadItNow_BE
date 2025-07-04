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
    public static final String BREADITNOW_EXCHANGE = "breaditnow.topic";
    public static final String STOCK_RESULT_QUEUE_NAME = "reservation.stock-result.queue";

    @Bean
    public MessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public TopicExchange breaditnowExchange() {
        return new TopicExchange(BREADITNOW_EXCHANGE);
    }

    @Bean
    public Queue stockResultQueue() {
        return new Queue(STOCK_RESULT_QUEUE_NAME, true);
    }

    @Bean
    public Binding bindingStockResult(Queue stockResultQueue, TopicExchange exchange) {
        return BindingBuilder.bind(stockResultQueue).to(exchange).with("v1.stock.update.#");
    }
}