package com.breaditnow.reservation.adapter.in.messaging;

import com.breaditnow.common.event.StockUpdateResultEvent;
import com.breaditnow.common.messaging.RabbitMQConstants;
import com.breaditnow.reservation.application.ReservationNotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static com.breaditnow.common.event.StockUpdateResultEvent.Status.SUCCESS;

@Slf4j
@Component
@RequiredArgsConstructor
public class StockDecreaseResultConsumer {
    private final ReservationNotificationService reservationNotificationService;

    @RabbitListener(queues = RabbitMQConstants.QUEUE_STOCK_DECREASE_RESULT)
    public void handleStockDecreaseResult(StockUpdateResultEvent resultEvent) {
        log.info("재고 감소 결과 수신: 예약 ID [{}], 상태 [{}]", resultEvent.reservationId(), resultEvent.status());
        if (resultEvent.status() == SUCCESS) {
            reservationNotificationService.requestSuccessNotification(resultEvent);
        } else {
            reservationNotificationService.handleFailedReservation(resultEvent);
        }
    }
}
