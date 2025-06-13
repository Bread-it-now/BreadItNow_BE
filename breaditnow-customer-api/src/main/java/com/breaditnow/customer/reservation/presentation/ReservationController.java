package com.breaditnow.customer.reservation.presentation;

import com.breaditnow.common.response.ApiSuccessResponse;
import com.breaditnow.customer.common.security.annotation.AuthCustomer;
import com.breaditnow.customer.reservation.application.ReservationService;
import com.breaditnow.customer.reservation.application.request.ReservationRequest;
import com.breaditnow.customer.reservation.infrastructure.ReservationAdapter;
import com.breaditnow.customer.reservation.presentation.response.ReservationDetailResponse;
import com.breaditnow.customer.reservation.presentation.response.ReservationSimpleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/reservation")
@RequiredArgsConstructor
public class ReservationController {
    private final ReservationService reservationService;
    private final ReservationAdapter reservationAdapter;

    @PostMapping
    public ApiSuccessResponse<Map<String, Long>> createReservation(@AuthCustomer Long customerId, @RequestBody ReservationRequest request) {
        Long reservationId = reservationService.createReservation(customerId, request);
        return ApiSuccessResponse.of(Map.of("reservationId", reservationId));
    }

    @GetMapping("/{reservationId}")
    public ApiSuccessResponse<ReservationSimpleResponse> getReservationSimple(@AuthCustomer Long customerId, @PathVariable Long reservationId) {
        return ApiSuccessResponse.of(reservationAdapter.getReservationSimple(customerId, reservationId));
    }

    @GetMapping("/{reservationId}/detail")
    public ApiSuccessResponse<ReservationDetailResponse> getReservationDetail(@AuthCustomer Long customerId, @PathVariable Long reservationId) {
        return ApiSuccessResponse.of(reservationAdapter.getReservationDetail(customerId, reservationId));
    }
}
