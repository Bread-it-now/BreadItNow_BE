package com.breaditnow.reservation.application.listener;

import com.breaditnow.reservation.application.event.ReservationCreatedEvent;
import com.breaditnow.reservation.domain.port.out.CustomerApiPort;
import com.breaditnow.reservation.domain.port.out.FcmPort;
import com.breaditnow.reservation.domain.port.out.OwnerApiPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
@RequiredArgsConstructor
public class ReservationEventListener {
    private final FcmPort fcmPort;
    private final CustomerApiPort customerApiPort;
    private final OwnerApiPort ownerApiPort;

    @TransactionalEventListener
    public void handleReservationCreatedEvent(ReservationCreatedEvent event) {
        log.info("Handling ReservationCreatedEvent for reservation ID: {}", event.reservationId());

        customerApiPort.findFcmTokenById(event.customerId()).ifPresent(customerFcmToken -> {
            String title = "📌 [예약 완료 안내]";
            String body = String.format(
                    "빵집 '%s'에 예약이 완료되었습니다.\n예약 상품: %s\n총 금액: %,d원",
                    event.bakeryName(),
                    String.join(", ", event.productNames()),
                    event.totalPrice()
            );
            fcmPort.sendNotification(customerFcmToken, title, body);
        });

        ownerApiPort.findFcmTokenById(event.ownerId()).ifPresent(ownerFcmToken -> {
            String title = "📢 [새 예약 도착]";
            String body = String.format(
                    "고객이 '%s'에 예약을 완료했습니다.\n예약 상품: %s\n총 금액: %,d원",
                    event.bakeryName(),
                    String.join(", ", event.productNames()),
                    event.totalPrice()
            );
            fcmPort.sendNotification(ownerFcmToken, title, body);
        });
    }
}
