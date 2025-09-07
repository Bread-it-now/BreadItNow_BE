package com.breaditnow.reservation.adapter.in.listener;

import com.breaditnow.common.domain.NotificationTypeDto;
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

import static com.breaditnow.common.domain.NotificationTypeDto.*;
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
                .notificationTypeDto(RESERVATION_REQUESTED)
                .customerNickName(reservation.getOrderer().getNickname())
                .productNames(productNames)
                .reservationTime(reservation.getReservationTime())
                .build();

        notificationEventPort.publish(notificationEvent);
    }

    @TransactionalEventListener(phase = AFTER_COMMIT)
    public void onReservationApproved(ReservationApprovedEvent event) {
        Reservation reservation = event.reservation();
        List<StockUpdateItem> stockUpdateItems = reservation.getReservationProducts().stream()
                .map(p -> new StockUpdateItem(p.getProductId(), p.getQuantity()))
                .toList();

        reservationEventPort.publishStockDecreaseRequest(new StockDecreaseRequestedEvent(reservation.getReservationId(), event.initiator(), APPROVED, stockUpdateItems, null));
    }

    @TransactionalEventListener(phase = AFTER_COMMIT)
    public void onReservationPartiallyApproved(ReservationPartiallyApprovedEvent event) {
        List<StockUpdateItem> stockUpdateItems = event.reservationProducts().stream()
                .map(p -> new StockUpdateItem(p.getProductId(), p.getQuantity()))
                .toList();

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

        reservationEventPort.publishStockIncreaseRequest(
                new StockIncreaseRequestedEvent(reservation.getReservationId(), event.initiator(), stockUpdateItems, CANCELLED, event.reason())
        );
    }

    @TransactionalEventListener(phase = AFTER_COMMIT)
    public void onCancellationNotificationRequired(CancellationNotificationRequiredEvent event) {
        Reservation reservation = event.reservation();
        UserIdentifier initiator = event.initiator();
        UserIdentifier recipient;
        NotificationTypeDto notificationTypeDto;

        if (initiator.type() == CUSTOMER) {
            recipient = new UserIdentifier(event.ownerId(), OWNER);
            notificationTypeDto = RESERVATION_CANCELED_BY_CUSTOMER;
        }
        else { // OWNER
            recipient = new UserIdentifier(reservation.getOrderer().getCustomerId(), CUSTOMER);
            notificationTypeDto = RESERVATION_CANCELED_BY_OWNER;
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
                .notificationTypeDto(notificationTypeDto)
                .productNames(productNames)
                .customerNickName(reservation.getOrderer().getNickname())
                .cancelReason(event.reason())
                .build();

        notificationEventPort.publish(notificationEvent);
    }

    @TransactionalEventListener(phase = AFTER_COMMIT)
    public void onNotificationRequired(NotificationRequiredEvent event) {
        notificationEventPort.publish(event);
    }
}
