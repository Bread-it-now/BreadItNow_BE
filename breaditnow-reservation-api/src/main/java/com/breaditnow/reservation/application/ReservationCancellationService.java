package com.breaditnow.reservation.application;

import com.breaditnow.common.domain.UserIdentifier;
import com.breaditnow.common.event.NotificationSendRequestedEvent;
import com.breaditnow.reservation.application.provider.ReservationProvider;
import com.breaditnow.reservation.domain.model.Reservation;
import com.breaditnow.reservation.domain.model.ReservationProduct;
import com.breaditnow.reservation.domain.port.out.NotificationEventPort;
import com.breaditnow.reservation.domain.port.out.ReservationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.breaditnow.common.domain.NotificationType.RESERVATION_CANCELED_BY_OWNER;
import static com.breaditnow.common.domain.ReservationStatus.CANCELLED;
import static com.breaditnow.common.domain.Role.CUSTOMER;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReservationCancellationService {
    private final ReservationProvider reservationProvider;
    private final ReservationRepository reservationRepository;
    private final NotificationEventPort notificationEventPort;

    public void finalizeCancellation(Long reservationId, UserIdentifier initiator, String cancelReason) {
        Reservation reservation = reservationProvider.provide(reservationId);

        if (reservation.getReservationState().getReservationStatus() == CANCELLED) {
            log.warn("이미 취소된 예약입니다. (예약 ID: {}). 중복 처리를 방지합니다.", reservationId);
            return;
        }

        reservation.cancel(cancelReason);
        reservationRepository.save(reservation);
        log.info("예약 ID [{}]의 상태를 CANCELLED로 최종 변경했습니다.", reservationId);

        sendCancellationNotification(reservation, initiator, cancelReason);
    }

    private void sendCancellationNotification(Reservation reservation, UserIdentifier initiator, String reason) {
        List<String> productNames = reservation.getReservationProducts().stream()
                .map(ReservationProduct::getProductName)
                .collect(Collectors.toList());

        UserIdentifier recipient = new UserIdentifier(reservation.getOrderer().getCustomerId(), CUSTOMER);

        NotificationSendRequestedEvent event = NotificationSendRequestedEvent.builder()
                .reservationId(reservation.getReservationId())
                .bakeryName(reservation.getReservedBakery().name())
                .productNames(productNames)
                .recipient(recipient)
                .initiator(initiator)
                .notificationType(RESERVATION_CANCELED_BY_OWNER)
                .cancelReason(reason)
                .build();

        notificationEventPort.publish(event);
    }
}
