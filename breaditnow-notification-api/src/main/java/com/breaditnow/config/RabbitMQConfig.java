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

import static com.breaditnow.common.messaging.RabbitMQConstants.*;

@Configuration
public class RabbitMQConfig {
    @Bean
    public MessageConverter messageConverter(ObjectMapper objectMapper) {
        return new Jackson2JsonMessageConverter(objectMapper);
    }

    @Bean
    public TopicExchange breaditnowTopicExchange() {
        return new TopicExchange(BREADITNOW_TOPIC_EXCHANGE, true, false);
    }

    @Bean
    public Queue notificationSendRequestQueue() {
        return new Queue(QUEUE_NOTIFICATION_SEND_REQUEST, true);
    }

    @Bean
    public Binding bindingNotificationSendRequest(Queue notificationSendRequestQueue, TopicExchange breaditnowTopicExchange) {
        return BindingBuilder.bind(notificationSendRequestQueue)
                .to(breaditnowTopicExchange)
                .with(ROUTING_KEY_NOTIFICATION_SEND_REQUEST);
    }
}