package com.breaditnow.reservation.application.dto.response;


import com.breaditnow.common.domain.DailyTime;
import com.breaditnow.common.domain.PageInfo;
import com.breaditnow.common.domain.ReservationStatus;
import com.breaditnow.reservation.domain.model.Reservation;
import org.springframework.data.domain.Page;

import java.util.List;

public record MyReservationPageResponse(
        List<ReservationSummary> reservations,
        PageInfo pageInfo
) {
    public static MyReservationPageResponse from(Page<Reservation> reservationPage) {
        List<ReservationSummary> reservationSummaries = reservationPage.getContent().stream()
                .map(ReservationSummary::from)
                .toList();

        return new MyReservationPageResponse(
                reservationSummaries,
                PageInfo.of(reservationPage)
        );
    }

    public record ReservationSummary(
            Long reservationId,
            String reservationDate,
            Long reservationNumber,
            ReservationStatus reservationStatus,
            Long bakeryId,
            String bakeryName,
            String bakeryProfileImageUrl,
            int reservationTotalPrice,
            String reservationPickupDeadline,
            String reservationCancellationReason,
            int reservationTotalProducts,
            String reservationMainProductName
    ) {
        public static ReservationSummary from(Reservation reservation) {
            String mainProductName = reservation.getReservationProducts().isEmpty() ? null : reservation.getReservationProducts().get(0).getProductName();
            return new ReservationSummary(
                    reservation.getReservationId(),
                    reservation.getReservationTime().format(DailyTime.DATE_FORMATTER),
                    reservation.getReservationNumber(),
                    reservation.getReservationState().getReservationStatus(),
                    reservation.getReservedBakery().bakeryId(),
                    reservation.getReservedBakery().name(),
                    reservation.getReservedBakery().profileImageUrl(),
                    reservation.getTotalPrice().getAmount(),
                    reservation.getPickupDeadline(),
                    reservation.getReservationState().getCancelReason(),
                    reservation.getReservationProducts().size(),
                    mainProductName
            );
        }
    }
}
