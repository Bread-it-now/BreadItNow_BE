package com.breaditnow.reservation.adapter.in.messaging;

import com.breaditnow.common.event.StockUpdateResultEvent;
import com.breaditnow.reservation.application.ReservationNotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static com.breaditnow.common.event.StockUpdateResultEvent.Status.SUCCESS;
import static com.breaditnow.config.RabbitMQConfig.STOCK_RESULT_QUEUE_NAME;

@Component
@RequiredArgsConstructor
public class ProductResultConsumer {
    private final ReservationNotificationService reservationNotificationService;

    @RabbitListener(queues = STOCK_RESULT_QUEUE_NAME)
    public void handleStockUpdateResult(StockUpdateResultEvent resultEvent) {
        if (resultEvent.status() == SUCCESS) {
            reservationNotificationService.requestSuccessNotification(resultEvent);
        } else {
            reservationNotificationService.handleFailedReservation(resultEvent);
        }
    }
}
