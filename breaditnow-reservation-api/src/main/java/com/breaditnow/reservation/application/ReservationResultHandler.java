package com.breaditnow.reservation.application;

import com.breaditnow.common.domain.NotificationTypeDto;
import com.breaditnow.common.domain.UserIdentifier;
import com.breaditnow.common.event.NotificationRequiredEvent;
import com.breaditnow.common.event.StockUpdateResultEvent;
import com.breaditnow.reservation.application.dto.internal.BakeryInfo;
import com.breaditnow.reservation.application.provider.BakeryProvider;
import com.breaditnow.reservation.application.provider.ReservationProvider;
import com.breaditnow.reservation.domain.model.Reservation;
import com.breaditnow.reservation.domain.model.ReservationProduct;
import com.breaditnow.reservation.domain.port.out.ReservationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.breaditnow.common.domain.NotificationTypeDto.*;
import static com.breaditnow.common.domain.ReservationStatus.CANCELLED;
import static com.breaditnow.common.domain.ReservationStatus.PARTIAL_APPROVED;
import static com.breaditnow.common.domain.Role.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReservationResultHandler {
    private final ReservationProvider reservationProvider;
    private final ReservationRepository reservationRepository;
    private final BakeryProvider bakeryProvider;
    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    public void finalizeCancellation(StockUpdateResultEvent resultEvent) {
        Reservation reservation = reservationProvider.provide(resultEvent.reservationId());
        BakeryInfo bakeryInfo = bakeryProvider.provide(reservation.getReservedBakery().bakeryId());
        if (reservation.getReservationState().getReservationStatus() == CANCELLED) {
            return;
        }

        reservation.cancel(resultEvent.message());
        reservationRepository.save(reservation);

        UserIdentifier initiator = resultEvent.initiator();
        UserIdentifier recipient;
        NotificationTypeDto notificationTypeDto;

        if (resultEvent.initiator().type() == CUSTOMER) {
            recipient = new UserIdentifier(bakeryInfo.ownerId(), OWNER);
            notificationTypeDto = RESERVATION_CANCELED_BY_CUSTOMER;
        }
        else { // OWNER
            recipient = new UserIdentifier(reservation.getOrderer().getCustomerId(), CUSTOMER);
            notificationTypeDto = RESERVATION_CANCELED_BY_OWNER;
        }

        List<String> productNames = reservation.getReservationProducts().stream()
                .map(ReservationProduct::getProductName)
                .toList();

        NotificationRequiredEvent notificationPayload = NotificationRequiredEvent.builder()
                .reservationId(reservation.getReservationId())
                .bakeryId(reservation.getReservedBakery().bakeryId())
                .recipient(recipient)
                .initiator(initiator)
                .notificationTypeDto(notificationTypeDto)
                .customerNickName(reservation.getOrderer().getNickname())
                .cancelReason(resultEvent.message())
                .bakeryName(bakeryInfo.name())
                .productNames(productNames)
                .build();

        eventPublisher.publishEvent(notificationPayload);
    }

    @Transactional
    public void finalizeApproval(StockUpdateResultEvent resultEvent) {
        Reservation reservation = reservationProvider.provide(resultEvent.reservationId());

        Long newReservationNumber = reservationRepository.getNextReservationNumber(reservation.getReservedBakery().bakeryId());
        UserIdentifier initiator = resultEvent.initiator();
        UserIdentifier recipient = new UserIdentifier(reservation.getOrderer().getCustomerId(), CUSTOMER);

        NotificationTypeDto notificationTypeDto = RESERVATION_APPROVED;
        if(resultEvent.reservationStatus() == PARTIAL_APPROVED){
            reservation.partialApprove(resultEvent.stockUpdateItems(), newReservationNumber, resultEvent.message());
            notificationTypeDto = RESERVATION_PARTIALLY_APPROVED;
        }
        else{
            reservation.approve(newReservationNumber);
        }
        reservationRepository.save(reservation);

        List<String> productNames = reservation.getReservationProducts().stream()
                .map(ReservationProduct::getProductName)
                .toList();

        NotificationRequiredEvent notificationPayload = NotificationRequiredEvent.builder()
                .reservationId(reservation.getReservationId())
                .bakeryId(reservation.getReservedBakery().bakeryId())
                .recipient(recipient)
                .initiator(initiator)
                .notificationTypeDto(notificationTypeDto)
                .customerNickName(reservation.getOrderer().getNickname())
                .bakeryName(reservation.getReservedBakery().name())
                .productNames(productNames)
                .reservationNumber(newReservationNumber)
                .pickupDeadline(reservation.calculatePickupDeadline())
                .build();

        eventPublisher.publishEvent(notificationPayload);
    }

    @Transactional
    public void handleApprovalFailure(StockUpdateResultEvent resultEvent) {
        log.warn("재고 처리 실패로 '승인 실패' 처리를 시작합니다. 예약 {}", resultEvent);

        Reservation reservation = reservationProvider.provide(resultEvent.reservationId());

        String cancelReason = "시스템 - 재고 부족";

        UserIdentifier initiator = new UserIdentifier(0L, SYSTEM);
        UserIdentifier recipient = resultEvent.initiator();

        List<String> productNames = reservation.getReservationProducts().stream()
                .map(ReservationProduct::getProductName)
                .toList();

        NotificationRequiredEvent notificationPayload = NotificationRequiredEvent.builder()
                .reservationId(reservation.getReservationId())
                .bakeryId(reservation.getReservedBakery().bakeryId())
                .recipient(recipient)
                .initiator(initiator)
                .notificationTypeDto(RESERVATION_APPROVAL_FAILED)
                .customerNickName(reservation.getOrderer().getNickname())
                .productNames(productNames)
                .cancelReason(cancelReason)
                .build();

        eventPublisher.publishEvent(notificationPayload);
    }
}
