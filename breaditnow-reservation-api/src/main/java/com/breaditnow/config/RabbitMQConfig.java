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
    public Queue stockDecreaseResultQueue() {
        return new Queue(RabbitMQConstants.QUEUE_STOCK_DECREASE_RESULT, true);
    }

    @Bean
    public Binding bindingStockDecreaseResult(Queue stockDecreaseResultQueue, TopicExchange breaditnowTopicExchange) {
        return BindingBuilder.bind(stockDecreaseResultQueue)
                .to(breaditnowTopicExchange)
                .with(RabbitMQConstants.ROUTING_KEY_STOCK_DECREASE_RESULT_WILDCARD);
    }

    @Bean
    public Queue stockIncreaseResultQueue() {
        return new Queue(RabbitMQConstants.QUEUE_STOCK_INCREASE_RESULT, true);
    }

    @Bean
    public Binding bindingStockIncreaseResult(Queue stockIncreaseResultQueue, TopicExchange breaditnowTopicExchange) {
        return BindingBuilder.bind(stockIncreaseResultQueue)
                .to(breaditnowTopicExchange)
                .with(RabbitMQConstants.ROUTING_KEY_STOCK_INCREASE_RESULT_WILDCARD);
    }
}