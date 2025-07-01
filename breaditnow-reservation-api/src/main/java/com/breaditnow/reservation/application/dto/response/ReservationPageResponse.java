package com.breaditnow.reservation.application.dto.response;

import com.breaditnow.common.domain.DailyTime;
import com.breaditnow.common.domain.PageInfo;
import com.breaditnow.common.domain.ReservationStatus;
import com.breaditnow.reservation.domain.model.Reservation;
import org.springframework.data.domain.Page;

import java.util.List;

import static com.breaditnow.common.domain.DailyTime.DATE_FORMATTER;

public record ReservationPageResponse(
        List<ReservationResponse> reservations,
        PageInfo pageInfo
) {
    public static ReservationPageResponse of(Page<Reservation> reservationPage) {
        List<ReservationResponse> reservationResponses = reservationPage.getContent().stream()
                .map(reservation -> new ReservationResponse(
                        reservation.getReservationId(),
                        reservation.getReservationTime().format(DATE_FORMATTER),
                        reservation.getReservationNumber(),
                        reservation.getReservationState().getReservationStatus(),
                        reservation.getOrderer().getNickname(),
                        reservation.getTotalPrice().getAmount(),
                        reservation.calculatePickupDeadline().format(DailyTime.DATE_FORMATTER)
                ))
                .toList();
        return new ReservationPageResponse(reservationResponses, PageInfo.of(reservationPage));
    }

    public record ReservationResponse(
            Long reservationId,
            String reservationDate,
            Long reservationNumber,
            ReservationStatus status,
            String consumerNickname,
            Integer totalPrice,
            String pickupDeadline
    ) {
    }
}
