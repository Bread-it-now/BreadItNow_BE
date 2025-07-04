package com.breaditnow.reservation.adapter.in.listener;

import com.breaditnow.common.domain.NotificationType;
import com.breaditnow.common.domain.UserIdentifier;
import com.breaditnow.common.dto.StockUpdateItem;
import com.breaditnow.common.event.NotificationRequiredEvent;
import com.breaditnow.common.event.StockDecreaseRequestedEvent;
import com.breaditnow.common.event.StockIncreaseRequestedEvent;
import com.breaditnow.reservation.application.event.*;
import com.breaditnow.reservation.domain.model.Reservation;
import com.breaditnow.reservation.domain.model.ReservationProduct;
import com.breaditnow.reservation.domain.port.out.NotificationEventPort;
import com.breaditnow.reservation.domain.port.out.ReservationEventPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.List;

import static com.breaditnow.common.domain.NotificationType.*;
import static com.breaditnow.common.domain.ReservationStatus.*;
import static com.breaditnow.common.domain.Role.CUSTOMER;
import static com.breaditnow.common.domain.Role.OWNER;
import static org.springframework.transaction.event.TransactionPhase.AFTER_COMMIT;

@Slf4j
@Component
@RequiredArgsConstructor
public class ReservationEventListener {
    private final ReservationEventPort reservationEventPort;
    private final NotificationEventPort notificationEventPort;

    @TransactionalEventListener(phase = AFTER_COMMIT)
    public void onReservationCreated(ReservationCreatedEvent event) {
        Reservation reservation = event.reservation();
        UserIdentifier initiator = new UserIdentifier(reservation.getOrderer().getCustomerId(), CUSTOMER);
        UserIdentifier recipient = new UserIdentifier(event.ownerId(), OWNER);

        List<String> productNames = reservation.getReservationProducts().stream()
                .map(ReservationProduct::getProductName)
                .toList();

        NotificationRequiredEvent notificationEvent = NotificationRequiredEvent.builder()
                .reservationId(reservation.getReservationId())
                .bakeryId(reservation.getReservedBakery().bakeryId())
                .recipient(recipient)
                .initiator(initiator)
                .notificationType(RESERVATION_REQUESTED)
                .customerNickName(reservation.getOrderer().getNickname())
                .productNames(productNames)
                .reservationTime(reservation.getReservationTime())
                .build();

        log.info("✅ [Event] 예약 생성 확인 -> 사장님에게 알림 발행 | 예약 ID: {}", reservation.getReservationId());
        notificationEventPort.publish(notificationEvent);
    }

    @TransactionalEventListener(phase = AFTER_COMMIT)
    public void onReservationApproved(ReservationApprovedEvent event) {
        Reservation reservation = event.reservation();
        List<StockUpdateItem> stockUpdateItems = reservation.getReservationProducts().stream()
                .map(p -> new StockUpdateItem(p.getProductId(), p.getQuantity()))
                .toList();

        log.info("✅ [Event] 예약 승인 확인 -> 재고 감소 요청 발행 | 예약 ID : {}", reservation.getReservationId());
        reservationEventPort.publishStockDecreaseRequest(new StockDecreaseRequestedEvent(reservation.getReservationId(), event.initiator(), APPROVED, stockUpdateItems, null));
    }

    @TransactionalEventListener(phase = AFTER_COMMIT)
    public void onReservationPartiallyApproved(ReservationPartiallyApprovedEvent event) {
        List<StockUpdateItem> stockUpdateItems = event.reservationProducts().stream()
                .map(p -> new StockUpdateItem(p.getProductId(), p.getQuantity()))
                .toList();

        log.info("✅ [Event] 예약 부분 승인 확인 -> 재고 감소 요청 발행 | 예약 ID: {}", event.reservationId());
        reservationEventPort.publishStockDecreaseRequest(
                new StockDecreaseRequestedEvent(event.reservationId(), event.initiator(), PARTIAL_APPROVED, stockUpdateItems, event.reason())
        );
    }

    @TransactionalEventListener(phase = AFTER_COMMIT)
    public void onStockRestoreRequired(StockRestoreRequiredEvent event) {
        Reservation reservation = event.reservation();
        List<StockUpdateItem> stockUpdateItems = reservation.getReservationProducts().stream()
                .map(p -> new StockUpdateItem(p.getProductId(), p.getQuantity()))
                .toList();

        log.info("✅ [Event] 재고 복구 필요 확인 -> 재고 증가 요청 발행 | 예약 ID: {}", reservation.getReservationId());
        reservationEventPort.publishStockIncreaseRequest(
                new StockIncreaseRequestedEvent(reservation.getReservationId(), event.initiator(), stockUpdateItems, CANCELLED, event.reason())
        );
    }

    @TransactionalEventListener(phase = AFTER_COMMIT)
    public void onCancellationNotificationRequired(CancellationNotificationRequiredEvent event) {
        Reservation reservation = event.reservation();
        UserIdentifier initiator = event.initiator();
        UserIdentifier recipient;
        NotificationType notificationType;

        if (initiator.type() == CUSTOMER) {
            recipient = new UserIdentifier(event.ownerId(), OWNER);
            notificationType = RESERVATION_CANCELED_BY_CUSTOMER;
            log.info("✅ [Event] 고객의 예약 취소 확인 -> 사장님에게 알림 발행 | 예약 ID: {}", reservation.getReservationId());
        }
        else { // OWNER
            recipient = new UserIdentifier(reservation.getOrderer().getCustomerId(), CUSTOMER);
            notificationType = RESERVATION_CANCELED_BY_OWNER;
            log.info("✅ [Event] 사장님의 예약 취소 확인 -> 고객에게 알림 발행 | 예약 ID: {}", reservation.getReservationId());
        }

        List<String> productNames = reservation.getReservationProducts().stream()
                .map(ReservationProduct::getProductName)
                .toList();

        NotificationRequiredEvent notificationEvent = NotificationRequiredEvent.builder()
                .reservationId(reservation.getReservationId())
                .bakeryId(reservation.getReservedBakery().bakeryId())
                .bakeryName(reservation.getReservedBakery().name())
                .recipient(recipient)
                .initiator(initiator)
                .notificationType(notificationType)
                .productNames(productNames)
                .customerNickName(reservation.getOrderer().getNickname())
                .cancelReason(event.reason())
                .build();

        notificationEventPort.publish(notificationEvent);
    }

    @TransactionalEventListener(phase = AFTER_COMMIT)
    public void onNotificationRequired(NotificationRequiredEvent event) {
        log.info("✅ [Event] 최종 알림 요청 확인 -> 알림 메시지 발행 | 예약 ID: {}", event.reservationId());
        notificationEventPort.publish(event);
    }
}
