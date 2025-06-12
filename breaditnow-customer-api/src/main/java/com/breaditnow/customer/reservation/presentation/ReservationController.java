package com.breaditnow.customer.reservation.presentation;

import com.breaditnow.common.response.ApiSuccessResponse;
import com.breaditnow.customer.common.security.annotation.AuthCustomer;
import com.breaditnow.customer.reservation.application.ReservationService;
import com.breaditnow.customer.reservation.application.request.ReservationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/reservation")
@RequiredArgsConstructor
public class ReservationController {
    private final ReservationService reservationService;

    @PostMapping
    public ApiSuccessResponse<Void> createReservation(@AuthCustomer Long customerId, @RequestBody ReservationRequest request) {
        reservationService.createReservation(customerId, request);
        return ApiSuccessResponse.of();
    }
}
