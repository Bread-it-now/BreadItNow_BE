package com.breaditnow.reservation.adapter.in.web;

import com.breaditnow.common.response.ApiSuccessResponse;
import com.breaditnow.reservation.adapter.in.resolver.AuthUser;
import com.breaditnow.reservation.adapter.in.resolver.AuthenticatedUser;
import com.breaditnow.reservation.domain.port.in.ApproveReservationUseCase;
import com.breaditnow.reservation.domain.port.in.CancelReservationUseCase;
import com.breaditnow.reservation.domain.port.in.CreateReservationUseCase;
import com.breaditnow.reservation.domain.port.in.ReservationQueryUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/bakery/{bakeryId}/reservation")
@RequiredArgsConstructor
public class BakeryReservationController {
    private final CreateReservationUseCase createReservationUseCase;
    private final CancelReservationUseCase cancelReservationUseCase;
    private final ApproveReservationUseCase approveReservationUseCase;
    private final ReservationQueryUseCase queryUseCase;

    @PostMapping("{reservationId}/approve")
    public ApiSuccessResponse<Void> approveReservation(
            @AuthUser AuthenticatedUser user,
            @PathVariable Long bakeryId,
            @PathVariable("reservationId") Long reservationId
    ) {
        approveReservationUseCase.approveReservation(user, bakeryId, reservationId);
        return ApiSuccessResponse.of();
    }
}
