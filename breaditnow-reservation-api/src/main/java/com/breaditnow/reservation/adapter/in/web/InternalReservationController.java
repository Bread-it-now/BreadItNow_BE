package com.breaditnow.reservation.adapter.in.web;

import com.breaditnow.common.response.ApiSuccessResponse;
import com.breaditnow.reservation.adapter.in.web.dto.request.ReservationCreateRequest;
import com.breaditnow.reservation.domain.port.in.CreateReservationUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/internal/api/v1/reservation")
@RequiredArgsConstructor
public class InternalReservationController {
    private final CreateReservationUseCase createReservationUseCase;

    @PostMapping
    public ApiSuccessResponse<Long> createReservation(
            @RequestHeader("X-Customer-Id") Long customerId,
            @RequestBody ReservationCreateRequest request
    ) {
        Long reservationId = createReservationUseCase.createReservation(customerId, request);
        return ApiSuccessResponse.of(reservationId);
    }
}
