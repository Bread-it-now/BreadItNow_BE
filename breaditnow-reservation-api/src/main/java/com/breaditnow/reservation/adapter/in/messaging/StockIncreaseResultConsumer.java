package com.breaditnow.reservation.adapter.in.messaging;

import com.breaditnow.common.event.StockUpdateResultEvent;
import com.breaditnow.reservation.application.ReservationCancellationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static com.breaditnow.common.messaging.RabbitMQConstants.QUEUE_STOCK_INCREASE_RESULT;

@Slf4j
@Component
@RequiredArgsConstructor
public class StockIncreaseResultConsumer {
    private final ReservationCancellationService cancellationService;

    @RabbitListener(queues = QUEUE_STOCK_INCREASE_RESULT)
    public void handleStockIncreaseResult(StockUpdateResultEvent resultEvent) {
        log.info("재고 증가(복구) 결과 수신: 예약 ID [{}], 상태 [{}]", resultEvent.reservationId(), resultEvent.status());
        cancellationService.finalizeCancellation(resultEvent.reservationId(), resultEvent.initiator(), resultEvent.message());
    }
}
