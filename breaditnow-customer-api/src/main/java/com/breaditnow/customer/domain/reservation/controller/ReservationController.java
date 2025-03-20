package com.breaditnow.customer.domain.reservation.controller;

import com.breaditnow.common.response.ApiSuccessResponse;
import com.breaditnow.customer.domain.reservation.controller.req.ReservationRequest;
import com.breaditnow.customer.domain.reservation.controller.res.ReservationResponse;
import com.breaditnow.customer.domain.reservation.service.ReservationService;
import com.breaditnow.customer.global.security.annotation.AuthCustomer;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/reservation")
@Slf4j
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping
    public ApiSuccessResponse<ReservationResponse> createReservation(
            @AuthCustomer Long customerId,
            @RequestBody @Valid ReservationRequest request) {
        return ApiSuccessResponse.of(reservationService.createReservation(customerId, request));
    }
}
