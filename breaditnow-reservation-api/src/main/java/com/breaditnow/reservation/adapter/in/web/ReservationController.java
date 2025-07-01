package com.breaditnow.reservation.adapter.in.web;

import com.breaditnow.common.domain.ReservationStatus;
import com.breaditnow.common.response.ApiSuccessResponse;
import com.breaditnow.reservation.adapter.in.resolver.AuthUser;
import com.breaditnow.reservation.adapter.in.resolver.AuthenticatedUser;
import com.breaditnow.reservation.application.dto.request.ReservationCancelRequest;
import com.breaditnow.reservation.application.dto.request.ReservationPartialApproveRequest;
import com.breaditnow.reservation.application.dto.response.ReservationPageResponse;
import com.breaditnow.reservation.domain.port.in.ApproveReservationUseCase;
import com.breaditnow.reservation.domain.port.in.CancelReservationUseCase;
import com.breaditnow.reservation.domain.port.in.PartialApproveReservationUseCase;
import com.breaditnow.reservation.domain.port.in.QueryReservationUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import static org.springframework.data.domain.Sort.Direction.DESC;

@RestController
@RequestMapping("/api/v1/bakery/{bakeryId}/reservation")
@RequiredArgsConstructor
public class ReservationController {
    private final ApproveReservationUseCase approveReservationUseCase;
    private final CancelReservationUseCase cancelReservationUseCase;
    private final PartialApproveReservationUseCase partialApproveReservationUseCase;
    private final QueryReservationUseCase queryReservationUseCase;

    @PostMapping("{reservationId}/approve")
    public ApiSuccessResponse<Void> approveReservation(
            @AuthUser AuthenticatedUser user,
            @PathVariable Long bakeryId,
            @PathVariable("reservationId") Long reservationId
    ) {
        approveReservationUseCase.approveReservation(user, bakeryId, reservationId);
        return ApiSuccessResponse.of();
    }

    @PostMapping("{reservationId}/cancel")
    public ApiSuccessResponse<Void> cancelReservation(
            @AuthUser AuthenticatedUser user,
            @PathVariable("reservationId") Long reservationId,
            @PathVariable("bakeryId") Long bakeryId,
            @RequestBody ReservationCancelRequest request
    ) {
        cancelReservationUseCase.cancelReservation(user, reservationId, bakeryId, request);
        return ApiSuccessResponse.of();
    }

    @PostMapping("{reservationId}/partial-approve")
    public ApiSuccessResponse<Void> partialApproveReservation(
            @AuthUser AuthenticatedUser user,
            @PathVariable("reservationId") Long reservationId,
            @PathVariable("bakeryId") Long bakeryId,
            @RequestBody ReservationPartialApproveRequest request
    ) {
        partialApproveReservationUseCase.partialApproveReservation(user, reservationId, bakeryId, request);
        return ApiSuccessResponse.of();
    }

    @GetMapping
    public ApiSuccessResponse<ReservationPageResponse> getReservations (
            @AuthUser AuthenticatedUser user,
            @PathVariable("bakeryId") Long bakeryId,
            @RequestParam(name = "status", required = false) ReservationStatus status,
            @PageableDefault(sort = "modifiedAt", direction = DESC) Pageable pageable
    ) {
        return ApiSuccessResponse.of(queryReservationUseCase.getMyReservations(user, bakeryId, pageable, status));
    }
}
