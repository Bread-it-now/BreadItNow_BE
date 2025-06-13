package com.breaditnow.customer.reservation.presentation.response;

import com.breaditnow.customer.common.domain.vo.PageInfo;
import com.breaditnow.customer.reservation.domain.ReservationStatus;
import com.breaditnow.customer.reservation.infrastructure.jpa.dto.ReservationWithBakery;
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
            Long reservationId,
            LocalDate reservationDate,
            Long reservationNumber,
            ReservationStatus status,
            Long bakeryId,
            String bakeryName,
            Integer totalPrice,
            LocalDateTime pickupDeadline,
            String cancellationReason,
            Integer totalReservationProducts,
            String mainReservationProductName
    ) {
        public static ReservationSummaryResponse of(ReservationWithBakery reservationWithBakery) {
            return new ReservationSummaryResponse(
                    reservationWithBakery.reservation().getId(),
                    reservationWithBakery.reservation().getReservationTime().toLocalDate(),
                    reservationWithBakery.reservation().getReservationNumber(),
                    reservationWithBakery.reservation().getReservationStatus(),
                    reservationWithBakery.bakery().getId(),
                    reservationWithBakery.bakery().getName(),
                    reservationWithBakery.reservation().getTotalPrice(),
                    reservationWithBakery.reservation().getPickupDeadLine(),
                    reservationWithBakery.reservation().getCancellationReason(),
                    reservationWithBakery.reservation().getReservationItems().size(),
                    reservationWithBakery.reservation().getReservationItems().get(0).getProductName()
            );
        }
    }
}
