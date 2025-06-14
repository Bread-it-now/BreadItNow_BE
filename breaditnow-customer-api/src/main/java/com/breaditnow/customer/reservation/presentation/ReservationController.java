package com.breaditnow.customer.reservation.presentation;

import com.breaditnow.common.response.ApiSuccessResponse;
import com.breaditnow.customer.common.security.annotation.AuthCustomer;
import com.breaditnow.customer.reservation.application.ReservationService;
import com.breaditnow.customer.reservation.application.request.CancelReasonRequest;
import com.breaditnow.customer.reservation.application.request.ReservationRequest;
import com.breaditnow.customer.reservation.application.request.ReservationSearchCriteria;
import com.breaditnow.customer.reservation.infrastructure.ReservationAdapter;
import com.breaditnow.customer.reservation.presentation.request.ReservationSearchRequest;
import com.breaditnow.customer.reservation.presentation.response.ReservationDetailResponse;
import com.breaditnow.customer.reservation.presentation.response.ReservationSimpleResponse;
import com.breaditnow.customer.reservation.presentation.response.ReservationSummaryPageResponse;
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

    @PatchMapping("/{reservation_id}/cancel")
    public ApiSuccessResponse<Void> cancelReservation(@AuthCustomer Long customerId, @PathVariable("reservation_id") Long reservationId, @RequestBody CancelReasonRequest request) {
        reservationService.cancelReservation(customerId, reservationId, request);
        return ApiSuccessResponse.of();
    }

    @GetMapping("/{reservation_id}")
    public ApiSuccessResponse<ReservationSimpleResponse> getReservationSimple(@AuthCustomer Long customerId, @PathVariable("reservation_id") Long reservationId) {
        return ApiSuccessResponse.of(reservationAdapter.getReservationSimple(customerId, reservationId));
    }

    @GetMapping("/{reservation_id}/detail")
    public ApiSuccessResponse<ReservationDetailResponse> getReservationDetail(@AuthCustomer Long customerId, @PathVariable("reservation_id") Long reservationId) {
        return ApiSuccessResponse.of(reservationAdapter.getReservationDetail(customerId, reservationId));
    }

    @GetMapping
    public ApiSuccessResponse<ReservationSummaryPageResponse> getReservations(@AuthCustomer Long customerId, ReservationSearchRequest request) {
        ReservationSearchCriteria criteria = ReservationSearchCriteria.of(request);
        return ApiSuccessResponse.of(reservationAdapter.getReservations(customerId, criteria));
    }
}
