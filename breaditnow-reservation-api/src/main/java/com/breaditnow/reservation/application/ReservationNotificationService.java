package com.breaditnow.reservation.application;

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

import static com.breaditnow.common.domain.NotificationType.RESERVATION_APPROVED;
import static com.breaditnow.common.domain.NotificationType.RESERVATION_CANCELED_BY_OWNER;
import static com.breaditnow.common.domain.Role.CUSTOMER;

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
        reservation.approve(newReservationNumber);
        reservationRepository.save(reservation);

        UserIdentifier initiator = resultEvent.initiator();
        UserIdentifier recipient = new UserIdentifier(reservation.getOrderer().getCustomerId(), CUSTOMER);


        List<String> productNames = reservation.getReservationProducts().stream()
                .map(ReservationProduct::getProductName)
                .toList();

        NotificationSendRequestedEvent notificationSendRequestedEvent = new NotificationSendRequestedEvent(
                reservation.getReservationId(),
                reservation.getReservedBakery().bakeryId(),
                recipient,
                initiator,
                RESERVATION_APPROVED,
                reservation.getOrderer().getNickname(),
                reservation.getReservedBakery().name(),
                productNames,
                reservation.getReservationNumber(),
                reservation.calculatePickupDeadline(),
                null
        );

        notificationEventPort.publish(notificationSendRequestedEvent);
    }

    @Transactional
    public void handleFailedReservation(StockUpdateResultEvent resultEvent) {
        log.warn("재고 처리 실패로 보상 트랜잭션을 시작합니다. 예약 ID: {}, 사유: {}", resultEvent.reservationId(), resultEvent.message());
        Reservation reservation = reservationProvider.provide(resultEvent.reservationId());

        String cancelReason = "재고 부족으로 예약이 자동 취소되었습니다.";
        reservation.cancel(cancelReason);
        reservationRepository.save(reservation);
        log.info("예약 상태를 '취소'로 변경했습니다. 예약 ID: {}", reservation.getReservationId());

        List<String> productNames = reservation.getReservationProducts().stream()
                .map(ReservationProduct::getProductName)
                .toList();

        UserIdentifier initiator = resultEvent.initiator();
        UserIdentifier recipient = new UserIdentifier(reservation.getOrderer().getCustomerId(), CUSTOMER);

        var eventToSend = new NotificationSendRequestedEvent(
                reservation.getReservationId(),
                reservation.getReservedBakery().bakeryId(),
                recipient,
                initiator,
                RESERVATION_CANCELED_BY_OWNER,
                reservation.getOrderer().getNickname(),
                reservation.getReservedBakery().name(),
                productNames,
                null,
                null,
                cancelReason
        );

        notificationEventPort.publish(eventToSend);
    }
}
