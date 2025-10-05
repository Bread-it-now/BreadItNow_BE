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

import java.util.Map;

import static com.breaditnow.common.messaging.RabbitMQConstants.*;

@Configuration
public class RabbitMQConfig {
    public static final String ACCOUNT_CREATED_QUEUE_NAME = "owner.account-created.queue";
    public static final String ACCOUNT_CREATED_ROUTING_KEY = "account.created";

    @Bean
    public TopicExchange dlqExchange() { return new TopicExchange(DLQ_EXCHANGE_NAME); }
    @Bean
    public Queue dlq() { return new Queue(DLQ_QUEUE_NAME); }
    @Bean
    public Binding dlqBinding() { return BindingBuilder.bind(dlq()).to(dlqExchange()).with("#"); }
    @Bean
    public MessageConverter messageConverter(ObjectMapper objectMapper) { return new Jackson2JsonMessageConverter(objectMapper); }

    @Bean
    public TopicExchange accountExchange() {
        return new TopicExchange("account.events.exchange");
    }

    @Bean
    public Queue accountCreatedQueue() {
        return new Queue(ACCOUNT_CREATED_QUEUE_NAME);
    }

    @Bean
    public Binding accountCreatedBinding(Queue accountCreatedQueue, TopicExchange accountExchange) {
        return BindingBuilder.bind(accountCreatedQueue)
                .to(accountExchange)
                .with(ACCOUNT_CREATED_ROUTING_KEY);
    }

    @Bean
    public TopicExchange breaditnowTopicExchange() { return new TopicExchange(BREADITNOW_TOPIC_EXCHANGE); }

    @Bean
    public Queue stockDecreaseRequestQueue() {
        return new Queue(QUEUE_STOCK_DECREASE_REQUEST, true, false, false,
                Map.of("x-dead-letter-exchange", DLQ_EXCHANGE_NAME));
    }

    @Bean
    public Binding bindingStockDecreaseRequest(Queue stockDecreaseRequestQueue, TopicExchange breaditnowTopicExchange) {
        return BindingBuilder.bind(stockDecreaseRequestQueue)
                .to(breaditnowTopicExchange)
                .with(ROUTING_KEY_STOCK_DECREASE_REQUEST);
    }

    @Bean
    public Queue stockIncreaseRequestQueue() {
        return new Queue(QUEUE_STOCK_INCREASE_REQUEST, true, false, false,
                Map.of("x-dead-letter-exchange", DLQ_EXCHANGE_NAME));
    }

    @Bean
    public Binding bindingStockIncreaseRequest(Queue stockIncreaseRequestQueue, TopicExchange breaditnowTopicExchange) {
        return BindingBuilder.bind(stockIncreaseRequestQueue)
                .to(breaditnowTopicExchange)
                .with(ROUTING_KEY_STOCK_INCREASE_REQUEST);
    }
}