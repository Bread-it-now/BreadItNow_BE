package com.breaditnow.customer.domain.reservation.controller.res;

import com.breaditnow.common.page.PageInfo;
import com.breaditnow.domain.domain.reservation.entity.Reservation;
import org.springframework.data.domain.Page;
import java.util.List;

public record ReservationPageResponse(
        List<ReservationListResponse> reservations,
        PageInfo pageInfo
) {
    public static ReservationPageResponse of(Page<Reservation> reservationsPage) {
        List<ReservationListResponse> reservations = reservationsPage.getContent().stream()
                .map(ReservationListResponse::of)
                .toList();

        return new ReservationPageResponse(
                reservations,
                PageInfo.of(
                        reservationsPage.getTotalElements(),
                        reservationsPage.getTotalPages(),
                        reservationsPage.isLast(),
                        reservationsPage.getNumber() + 1
                )
        );
    }
}
