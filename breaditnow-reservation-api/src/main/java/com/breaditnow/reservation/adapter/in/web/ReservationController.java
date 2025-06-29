package com.breaditnow.reservation.adapter.in.web;

import com.breaditnow.common.response.ApiSuccessResponse;
import com.breaditnow.reservation.adapter.in.resolver.AuthUser;
import com.breaditnow.reservation.adapter.in.resolver.AuthenticatedUser;
import com.breaditnow.reservation.application.dto.request.ReservationCancelRequest;
import com.breaditnow.reservation.application.dto.request.ReservationCreateRequest;
import com.breaditnow.reservation.application.dto.response.MySimpleReservationResponse;
import com.breaditnow.reservation.domain.port.in.ApproveReservationUseCase;
import com.breaditnow.reservation.domain.port.in.CancelReservationUseCase;
import com.breaditnow.reservation.domain.port.in.CreateReservationUseCase;
import com.breaditnow.reservation.domain.port.in.ReservationQueryUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/reservation")
@RequiredArgsConstructor
public class ReservationController {
    private final CreateReservationUseCase createReservationUseCase;
    private final CancelReservationUseCase cancelReservationUseCase;
    private final ApproveReservationUseCase approveReservationUseCase;
    private final ReservationQueryUseCase queryUseCase;

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

    @PostMapping("{reservationId}/approve")
    public ApiSuccessResponse<Void> approveReservation(
            @AuthUser AuthenticatedUser user,
            @PathVariable("reservationId") Long reservationId
    ) {
        approveReservationUseCase.approveReservation(user, reservationId);
        return ApiSuccessResponse.of();
    }

    @GetMapping("/{reservationId}")
    public ApiSuccessResponse<MySimpleReservationResponse> getSimpleReservation(@AuthUser AuthenticatedUser user, @PathVariable Long reservationId) {
        return ApiSuccessResponse.of(queryUseCase.getSimpleReservation(user, reservationId));
    }
}
