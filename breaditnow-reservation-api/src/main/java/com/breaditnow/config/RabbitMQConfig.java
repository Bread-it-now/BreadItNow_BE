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


    // --- 예약 서비스가 '구독(수신)'할 큐들 ---

    // 상품 서비스로부터 재고 감소 '결과'를 수신할 큐
    @Bean
    public Queue stockDecreaseResultQueue() {
        return new Queue(QUEUE_STOCK_DECREASE_RESULT, true, false, false, Map.of("x-dead-letter-exchange", DLQ_EXCHANGE_NAME));
    }

    // v1.stock.decrease.* 패턴의 모든 결과 메시지를 수신
    @Bean
    public Binding bindingStockDecreaseResult(Queue stockDecreaseResultQueue, TopicExchange breaditnowTopicExchange) {
        return BindingBuilder.bind(stockDecreaseResultQueue)
                .to(breaditnowTopicExchange)
                .with(ROUTING_KEY_STOCK_DECREASE_RESULT_WILDCARD);
    }

    // 상품 서비스로부터 재고 증가 '결과'를 수신할 큐
    @Bean
    public Queue stockIncreaseResultQueue() {
        return new Queue(QUEUE_STOCK_INCREASE_RESULT, true, false, false,
                Map.of("x-dead-letter-exchange", DLQ_EXCHANGE_NAME));
    }

    // v1.stock.increase.* 패턴의 모든 결과 메시지를 수신
    @Bean
    public Binding bindingStockIncreaseResult(Queue stockIncreaseResultQueue, TopicExchange breaditnowTopicExchange) {
        return BindingBuilder.bind(stockIncreaseResultQueue)
                .to(breaditnowTopicExchange)
                .with(ROUTING_KEY_STOCK_INCREASE_RESULT_WILDCARD);
    }
}