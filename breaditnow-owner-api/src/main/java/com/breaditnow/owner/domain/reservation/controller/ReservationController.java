package com.breaditnow.owner.domain.reservation.controller;

import com.breaditnow.common.response.ApiSuccessResponse;
import com.breaditnow.domain.domain.reservation.enumerate.ReservationRequestStatus;
import com.breaditnow.owner.domain.reservation.controller.res.ReservationDetailResponse;
import com.breaditnow.owner.domain.reservation.controller.res.ReservationPageResponse;
import com.breaditnow.owner.domain.reservation.service.ReservationService;
import com.breaditnow.owner.global.security.annotation.AuthOwner;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/reservation/owner")
@Slf4j
public class ReservationController {

    private final ReservationService reservationService;

    @GetMapping
    public ApiSuccessResponse<ReservationPageResponse> getReservationsForOwner(
            @AuthOwner Long ownerId,
            @RequestParam(name = "status", required = false, defaultValue = "ALL") ReservationRequestStatus status,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size
    ) {
        return ApiSuccessResponse.of(reservationService.getReservationsForOwner(ownerId, status, page, size));
    }

    @GetMapping("/{reservationId}")
    public ApiSuccessResponse<ReservationDetailResponse> getReservationDetailForOwner(
            @AuthOwner Long ownerId,
            @PathVariable("reservationId") Long reservationId
    ) {
        return ApiSuccessResponse.of(reservationService.getReservationDetailForOwner(ownerId, reservationId));
    }
}
