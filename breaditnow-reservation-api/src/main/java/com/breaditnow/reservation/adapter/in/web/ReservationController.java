package com.breaditnow.reservation.adapter.in.web;

import com.breaditnow.common.response.ApiSuccessResponse;
import com.breaditnow.common.security.AuthUser;
import com.breaditnow.common.security.AuthenticatedUser;
import com.breaditnow.reservation.application.dto.request.ReservationCancelRequest;
import com.breaditnow.reservation.application.dto.request.ReservationCreateRequest;
import com.breaditnow.reservation.domain.port.in.CancelReservationUseCase;
import com.breaditnow.reservation.domain.port.in.CreateReservationUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/reservation")
@RequiredArgsConstructor
public class ReservationController {
    private final CreateReservationUseCase createReservationUseCase;
    private final CancelReservationUseCase cancelReservationUseCase;

    @PostMapping
    public ApiSuccessResponse<Long> createReservation(@AuthUser AuthenticatedUser user, @RequestBody ReservationCreateRequest request) {
        Long reservationId = createReservationUseCase.createReservation(user, request);
        return ApiSuccessResponse.of(reservationId);
    }

    @PatchMapping("{reservationId}/cancel")
    public ApiSuccessResponse<Void> cancelReservation(
            @AuthUser AuthenticatedUser user,
            @PathVariable("reservationId") Long reservationId,
            @RequestBody ReservationCancelRequest request
    ) {
        cancelReservationUseCase.cancelReservation(user, reservationId, request);
        return ApiSuccessResponse.of();
    }
}
