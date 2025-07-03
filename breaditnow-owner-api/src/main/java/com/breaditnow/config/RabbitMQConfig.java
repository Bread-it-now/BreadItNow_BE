package com.breaditnow.config;

import com.fasterxml.jackson.databind.ObjectMapper;
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

    public static final String EXCHANGE_NAME = "breaditnow.topic";

    private static final String DECREASE_STOCK_QUEUE_NAME = "product.stock-decrease.queue";
    private static final String DECREASE_STOCK_ROUTING_KEY = "v1.stock.decrease.requested";

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(EXCHANGE_NAME, true, false);
    }

    @Bean
    public Queue decreaseStockQueue() {
        return new Queue(DECREASE_STOCK_QUEUE_NAME, true);
    }

    @Bean
    public Binding bindingDecreaseStock(Queue decreaseStockQueue, TopicExchange exchange) {
        return BindingBuilder.bind(decreaseStockQueue).to(exchange).with(DECREASE_STOCK_ROUTING_KEY);
    }

    @Bean
    public MessageConverter messageConverter(ObjectMapper objectMapper) {
        return new Jackson2JsonMessageConverter(objectMapper);
    }
}