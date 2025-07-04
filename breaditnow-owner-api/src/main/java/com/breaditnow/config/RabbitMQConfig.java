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

    // --- 공통 설정 (DLQ, MessageConverter, Exchange) ---
    @Bean
    public TopicExchange dlqExchange() { return new TopicExchange(DLQ_EXCHANGE_NAME); }
    @Bean
    public Queue dlq() { return new Queue(DLQ_QUEUE_NAME); }
    @Bean
    public Binding dlqBinding() { return BindingBuilder.bind(dlq()).to(dlqExchange()).with("#"); }
    @Bean
    public MessageConverter messageConverter(ObjectMapper objectMapper) { return new Jackson2JsonMessageConverter(objectMapper); }
    @Bean
    public TopicExchange breaditnowTopicExchange() { return new TopicExchange(BREADITNOW_TOPIC_EXCHANGE); }


    // --- 상품 서비스가 '구독(수신)'할 큐들 ---

    @Bean
    public Queue stockDecreaseRequestQueue() {
        // 예약 서비스로부터 재고 감소 '요청'을 수신할 큐
        return new Queue(QUEUE_STOCK_DECREASE_REQUEST, true, false, false,
                Map.of("x-dead-letter-exchange", DLQ_EXCHANGE_NAME));
    }

    @Bean
    public Binding bindingStockDecreaseRequest(Queue stockDecreaseRequestQueue, TopicExchange breaditnowTopicExchange) {
        // v1.stock.decrease.requested 키를 가진 요청 메시지를 수신
        return BindingBuilder.bind(stockDecreaseRequestQueue)
                .to(breaditnowTopicExchange)
                .with(ROUTING_KEY_STOCK_DECREASE_REQUEST);
    }

    @Bean
    public Queue stockIncreaseRequestQueue() {
        // 예약 서비스로부터 재고 증가 '요청'을 수신할 큐
        return new Queue(QUEUE_STOCK_INCREASE_REQUEST, true, false, false,
                Map.of("x-dead-letter-exchange", DLQ_EXCHANGE_NAME));
    }

    @Bean
    public Binding bindingStockIncreaseRequest(Queue stockIncreaseRequestQueue, TopicExchange breaditnowTopicExchange) {
        // v1.stock.increase.requested 키를 가진 요청 메시지를 수신
        return BindingBuilder.bind(stockIncreaseRequestQueue)
                .to(breaditnowTopicExchange)
                .with(ROUTING_KEY_STOCK_INCREASE_REQUEST);
    }
}