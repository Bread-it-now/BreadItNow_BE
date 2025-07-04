package com.breaditnow.config;

import com.breaditnow.common.messaging.RabbitMQConstants;
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

    @Bean
    public MessageConverter messageConverter(ObjectMapper objectMapper) {
        return new Jackson2JsonMessageConverter(objectMapper);
    }

    @Bean
    public TopicExchange breaditnowTopicExchange() {
        return new TopicExchange(RabbitMQConstants.BREADITNOW_TOPIC_EXCHANGE, true, false);
    }

    @Bean
    public Queue stockDecreaseRequestQueue() {
        return new Queue(RabbitMQConstants.QUEUE_STOCK_DECREASE_REQUEST, true);
    }

    @Bean
    public Binding bindingStockDecreaseRequest(Queue stockDecreaseRequestQueue, TopicExchange breaditnowTopicExchange) {
        return BindingBuilder.bind(stockDecreaseRequestQueue)
                .to(breaditnowTopicExchange)
                .with(RabbitMQConstants.ROUTING_KEY_STOCK_DECREASE_REQUEST);
    }

    @Bean
    public Queue stockIncreaseRequestQueue() {
        return new Queue(RabbitMQConstants.QUEUE_STOCK_INCREASE_REQUEST, true);
    }

    @Bean
    public Binding bindingStockIncreaseRequest(Queue stockIncreaseRequestQueue, TopicExchange breaditnowTopicExchange) {
        return BindingBuilder.bind(stockIncreaseRequestQueue)
                .to(breaditnowTopicExchange)
                .with(RabbitMQConstants.ROUTING_KEY_STOCK_INCREASE_REQUEST);
    }
}