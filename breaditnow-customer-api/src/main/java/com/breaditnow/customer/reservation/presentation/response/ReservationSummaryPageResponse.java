package com.breaditnow.customer.reservation.presentation.response;

import com.breaditnow.customer.common.domain.vo.PageInfo;
import com.breaditnow.customer.reservation.domain.ReservationStatus;
import com.breaditnow.customer.reservation.infrastructure.jpa.ReservationWithBakery;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public record ReservationSummaryPageResponse(List<ReservationSummaryResponse> reservations, PageInfo pageInfo) {
    public static ReservationSummaryPageResponse of(Page<ReservationWithBakery> reservationWithBakeries) {
        if(reservationWithBakeries.isEmpty()) {
            return new ReservationSummaryPageResponse(List.of(), PageInfo.empty());
        }

        List<ReservationSummaryResponse> reservationSummaryResponses = reservationWithBakeries.stream()
                .map(ReservationSummaryResponse::of)
                .toList();

        PageInfo pageInfo = PageInfo.of(reservationWithBakeries);

        return new ReservationSummaryPageResponse(reservationSummaryResponses, pageInfo);
    }

    public record ReservationSummaryResponse(
            Long bakeryId,
            String bakeryName,
            String bakeryProfileImageUrl,
            Long reservationId,
            LocalDate reservationDate,
            Long reservationNumber,
            ReservationStatus reservationStatus,
            Integer reservationTotalPrice,
            LocalDateTime reservationPickupDeadline,
            String reservationCancellationReason,
            Integer reservationTotalProducts,
            String reservationMainProductName
    ) {
        public static ReservationSummaryResponse of(ReservationWithBakery reservationWithBakery) {
            return new ReservationSummaryResponse(
                    reservationWithBakery.bakery().getId(),
                    reservationWithBakery.bakery().getName(),
                    reservationWithBakery.bakery().getProfileImage(),
                    reservationWithBakery.reservation().getId(),
                    reservationWithBakery.reservation().getReservationTime().toLocalDate(),
                    reservationWithBakery.reservation().getReservationNumber(),
                    reservationWithBakery.reservation().getReservationStatus(),
                    reservationWithBakery.reservation().getTotalPrice(),
                    reservationWithBakery.reservation().getPickupDeadLine(),
                    reservationWithBakery.reservation().getCancellationReason(),
                    reservationWithBakery.reservation().getReservationItems().size(),
                    reservationWithBakery.reservation().getReservationItems().isEmpty() ? null :
                            reservationWithBakery.reservation().getReservationItems().get(0).getProductName()
            );
        }
    }
}
