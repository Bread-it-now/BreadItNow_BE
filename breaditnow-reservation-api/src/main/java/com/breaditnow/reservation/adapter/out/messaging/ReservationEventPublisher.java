//package com.breaditnow.reservation.adapter.out.messaging;
//
//import com.breaditnow.config.RabbitMQConfig;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.stereotype.Component;
//
//@Slf4j
//@Component
//@RequiredArgsConstructor
//public class ReservationEventPublisher implements PublishReservationEventPort {
//    private final RabbitTemplate rabbitTemplate;
//
//    @Override
//    public void publishReservationCreated(ReservationCreatedEvent event) {
//        log.info(">>> Publishing ReservationCreatedEvent for reservationId: {}", event.reservationId());
//        rabbitTemplate.convertAndSend(
//                RabbitMQConfig.RESERVATION_EVENT_EXCHANGE,
//                RabbitMQConfig.RESERVATION_CREATED_ROUTING_KEY,
//                event
//        );
//    }
//}
