package com.breaditnow.customer.reservation.presentation;

import com.breaditnow.common.response.ApiSuccessResponse;
import com.breaditnow.customer.common.security.annotation.AuthCustomer;
import com.breaditnow.customer.reservation.application.ReservationService;
import com.breaditnow.customer.reservation.application.request.ReservationRequest;
import com.breaditnow.customer.reservation.infrastructure.ReservationAdapter;
import com.breaditnow.customer.reservation.presentation.response.ReservationDetailResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/reservation")
@RequiredArgsConstructor
public class ReservationController {
    private final ReservationService reservationService;
    private final ReservationAdapter reservationAdapter;

    @PostMapping
    public ApiSuccessResponse<Void> createReservation(@AuthCustomer Long customerId, @RequestBody ReservationRequest request) {
        reservationService.createReservation(customerId, request);
        return ApiSuccessResponse.of();
    }

    @GetMapping("/{reservationId}")
    public ApiSuccessResponse<ReservationDetailResponse> getReservation(@AuthCustomer Long customerId, @PathVariable Long reservationId) {
        return ApiSuccessResponse.of(reservationAdapter.getReservationDetail(customerId, reservationId));
    }
}
