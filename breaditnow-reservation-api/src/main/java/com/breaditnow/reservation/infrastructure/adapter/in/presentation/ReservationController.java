package com.breaditnow.reservation.infrastructure.adapter.in.presentation;

import com.breaditnow.common.response.ApiSuccessResponse;
import com.breaditnow.reservation.application.dto.request.ReservationCreateRequest;
import com.breaditnow.reservation.application.dto.response.ReservationCreateResponse;
import com.breaditnow.reservation.application.port.in.CreateReservationUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/reservation")
@RequiredArgsConstructor
public class ReservationController {

    private final CreateReservationUseCase createReservationUseCase;

    @PostMapping
    public ApiSuccessResponse<ReservationCreateResponse> createReservation(
            @RequestHeader("X-Authorization-Id") Long customerId,
            @RequestBody ReservationCreateRequest request
    ) {
        return ApiSuccessResponse.of(createReservationUseCase.create(customerId, request));
    }
}
