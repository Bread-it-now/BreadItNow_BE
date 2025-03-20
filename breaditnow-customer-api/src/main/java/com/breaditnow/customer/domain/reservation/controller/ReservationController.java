package com.breaditnow.customer.domain.reservation.controller;

import com.breaditnow.common.response.ApiSuccessResponse;
import com.breaditnow.customer.domain.reservation.controller.req.ReservationRequest;
import com.breaditnow.customer.domain.reservation.controller.res.ReservationPageResponse;
import com.breaditnow.customer.domain.reservation.controller.res.ReservationResponse;
import com.breaditnow.customer.domain.reservation.service.ReservationService;
import com.breaditnow.customer.global.security.annotation.AuthCustomer;
import com.breaditnow.domain.domain.reservation.enumerate.ReservationRequestStatus;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping
    public ApiSuccessResponse<ReservationPageResponse> getReservations(
            @AuthCustomer Long customerId,
            @RequestParam(name = "status",defaultValue = "ALL") ReservationRequestStatus status,
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "size", defaultValue = "10") int size
    ){
        return ApiSuccessResponse.of(reservationService.getReservations(customerId, status, page, size));
    }
}
