package com.breaditnow.customer.reservation.presentation.response;

import com.breaditnow.customer.common.domain.vo.PageInfo;
import com.breaditnow.customer.reservation.domain.ReservationStatus;
import com.breaditnow.customer.reservation.infrastructure.jpa.ReservationDto;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public record ReservationSummaryPageResponse(List<ReservationSummaryResponse> reservations, PageInfo pageInfo) {
    public static ReservationSummaryPageResponse of(Page<ReservationDto> reservationWithBakeries) {
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
        public static ReservationSummaryResponse of(ReservationDto reservationDto) {
            return new ReservationSummaryResponse(
                    reservationDto.bakery().getId(),
                    reservationDto.bakery().getName(),
                    reservationDto.bakery().getProfileImage(),
                    reservationDto.reservation().getId(),
                    reservationDto.reservation().getReservationTime().toLocalDate(),
                    reservationDto.reservation().getReservationNumber(),
                    reservationDto.reservation().getReservationStatus(),
                    reservationDto.reservation().getTotalPrice(),
                    reservationDto.reservation().getPickupDeadLine(),
                    reservationDto.reservation().getCancellationReason(),
                    reservationDto.reservation().getReservationItems().size(),
                    reservationDto.reservation().getReservationItems().isEmpty() ? null :
                            reservationDto.reservation().getReservationItems().get(0).getProductName()
            );
        }
    }
}
