package com.breaditnow.reservation.adapter.in.web;

import com.breaditnow.common.response.ApiSuccessResponse;
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
    public ApiSuccessResponse<Long> createReservation(
            @RequestHeader("X-Authorization-Id") Long customerId,
            @RequestBody ReservationCreateRequest request
    ) {
        Long reservationId = createReservationUseCase.createReservation(customerId, request);
        return ApiSuccessResponse.of(reservationId);
    }

    @PatchMapping("{reservationId}/cancel")
    public ApiSuccessResponse<Void> cancelReservation(
            @RequestHeader("X-Authorization-Id") Long userId,
            @RequestHeader("X-Authorization-Role") String roleString,
            @PathVariable("reservationId") Long reservationId,
            @RequestBody ReservationCancelRequest request
    ) {
        cancelReservationUseCase.cancelReservation(userId, roleString, reservationId, request);
        return ApiSuccessResponse.of();
    }
}
