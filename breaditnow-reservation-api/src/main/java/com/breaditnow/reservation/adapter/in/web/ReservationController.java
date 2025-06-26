package com.breaditnow.reservation.adapter.in.web;

import com.breaditnow.common.response.ApiSuccessResponse;
import com.breaditnow.reservation.adapter.in.web.dto.request.ReservationCreateRequest;
import com.breaditnow.reservation.domain.port.in.CreateReservationUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/reservation")
@RequiredArgsConstructor
public class ReservationController {
    private final CreateReservationUseCase createReservationUseCase;

    @PostMapping
    public ApiSuccessResponse<Map<String, Long>> createReservation(
            @RequestHeader("X-Authorization-Id") Long customerId,
            @RequestBody ReservationCreateRequest request
    ) {
        Long reservationId = createReservationUseCase.createReservation(customerId, request);
        return ApiSuccessResponse.of("reservationId", reservationId);
    }
}
