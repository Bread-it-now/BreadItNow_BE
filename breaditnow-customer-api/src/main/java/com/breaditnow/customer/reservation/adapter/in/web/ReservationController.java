package com.breaditnow.customer.reservation.adapter.in.web;

import com.breaditnow.common.response.ApiSuccessResponse;
import com.breaditnow.customer.common.security.annotation.AuthCustomer;
import com.breaditnow.customer.reservation.application.dto.request.ReservationCreateRequest;
import com.breaditnow.customer.reservation.domain.in.CreateReservationUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/reservation")
@RequiredArgsConstructor
public class ReservationController {
    private final CreateReservationUseCase createReservationUseCase;

    @PostMapping
    public ApiSuccessResponse<Map<String, Long>> createReservation(
            @AuthCustomer Long customerId,
            @RequestBody ReservationCreateRequest request
    ) {
        Long reservationId = createReservationUseCase.createReservation(customerId, request);
        return ApiSuccessResponse.of("reservationId", reservationId);
    }
}
