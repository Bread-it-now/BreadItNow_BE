package com.breaditnow.reservation.application;

import com.breaditnow.common.domain.NotificationType;
import com.breaditnow.common.domain.UserIdentifier;
import com.breaditnow.common.event.NotificationSendRequestedEvent;
import com.breaditnow.common.event.StockUpdateResultEvent;
import com.breaditnow.reservation.application.provider.ReservationProvider;
import com.breaditnow.reservation.domain.model.Reservation;
import com.breaditnow.reservation.domain.model.ReservationProduct;
import com.breaditnow.reservation.domain.port.out.NotificationEventPort;
import com.breaditnow.reservation.domain.port.out.ReservationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.breaditnow.common.domain.NotificationType.*;
import static com.breaditnow.common.domain.ReservationStatus.PARTIAL_APPROVED;
import static com.breaditnow.common.domain.Role.CUSTOMER;
import static com.breaditnow.common.domain.Role.SYSTEM;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReservationNotificationService {
    private final NotificationEventPort notificationEventPort;
    private final ReservationProvider reservationProvider;
    private final ReservationRepository reservationRepository;

    @Transactional
    public void requestSuccessNotification(StockUpdateResultEvent resultEvent){
        Reservation reservation = reservationProvider.provide(resultEvent.reservationId());

        Long newReservationNumber = reservationRepository.getNextReservationNumber(reservation.getReservedBakery().bakeryId());
        UserIdentifier initiator = resultEvent.initiator();
        UserIdentifier recipient = new UserIdentifier(reservation.getOrderer().getCustomerId(), CUSTOMER);

        NotificationType notificationType = RESERVATION_APPROVED;
        if(resultEvent.reservationStatus() == PARTIAL_APPROVED){
            reservation.partialApprove(resultEvent.stockUpdateItems(), newReservationNumber, resultEvent.message());
            notificationType = RESERVATION_PARTIALLY_APPROVED;
        }
        else{
            reservation.approve(newReservationNumber);
        }
        reservationRepository.save(reservation);

        List<String> productNames = reservation.getReservationProducts().stream()
                .map(ReservationProduct::getProductName)
                .toList();

        NotificationSendRequestedEvent notificationSendRequestedEvent = NotificationSendRequestedEvent.builder()
                .reservationId(reservation.getReservationId())
                .bakeryId(reservation.getReservedBakery().bakeryId())
                .recipient(recipient)
                .initiator(initiator)
                .notificationType(notificationType)
                .customerNickName(reservation.getOrderer().getNickname())
                .bakeryName(reservation.getReservedBakery().name())
                .productNames(productNames)
                .reservationNumber(newReservationNumber)
                .pickupDeadline(reservation.calculatePickupDeadline())
                .build();
        notificationEventPort.publish(notificationSendRequestedEvent);
    }

    @Transactional
    public void handleFailedReservation(StockUpdateResultEvent resultEvent) {
        log.warn("재고 처리 실패로 '승인 실패' 처리를 시작합니다. 예약 {}", resultEvent);

        Reservation reservation = reservationProvider.provide(resultEvent.reservationId());

        String cancelReason = "(시스템)재고 부족";

        UserIdentifier initiator = new UserIdentifier(0L, SYSTEM);
        UserIdentifier recipient = resultEvent.initiator();

        List<String> productNames = reservation.getReservationProducts().stream()
                .map(ReservationProduct::getProductName)
                .toList();

        var eventToSend = NotificationSendRequestedEvent.builder()
                .reservationId(reservation.getReservationId())
                .bakeryId(reservation.getReservedBakery().bakeryId())
                .recipient(recipient)
                .initiator(initiator)
                .notificationType(RESERVATION_APPROVAL_FAILED)
                .customerNickName(reservation.getOrderer().getNickname())
                .productNames(productNames)
                .cancelReason(cancelReason)
                .build();

        notificationEventPort.publish(eventToSend);
    }
}
