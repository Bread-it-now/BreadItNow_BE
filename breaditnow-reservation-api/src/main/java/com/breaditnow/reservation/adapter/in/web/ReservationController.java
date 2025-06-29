package com.breaditnow.reservation.adapter.in.web;

import com.breaditnow.common.domain.ReservationStatus;
import com.breaditnow.common.response.ApiSuccessResponse;
import com.breaditnow.reservation.adapter.in.resolver.AuthUser;
import com.breaditnow.reservation.adapter.in.resolver.AuthenticatedUser;
import com.breaditnow.reservation.application.dto.request.ReservationCancelRequest;
import com.breaditnow.reservation.application.dto.request.ReservationCreateRequest;
import com.breaditnow.reservation.application.dto.response.MyReservationDetailResponse;
import com.breaditnow.reservation.application.dto.response.MyReservationPageResponse;
import com.breaditnow.reservation.application.dto.response.MyReservationSimpleResponse;
import com.breaditnow.reservation.domain.port.in.ApproveReservationUseCase;
import com.breaditnow.reservation.domain.port.in.CancelReservationUseCase;
import com.breaditnow.reservation.domain.port.in.CreateReservationUseCase;
import com.breaditnow.reservation.domain.port.in.ReservationQueryUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/reservation")
@RequiredArgsConstructor
public class ReservationController {
    private final CreateReservationUseCase createReservationUseCase;
    private final CancelReservationUseCase cancelReservationUseCase;
    private final ApproveReservationUseCase approveReservationUseCase;
    private final ReservationQueryUseCase queryUseCase;

    @PostMapping
    public ApiSuccessResponse<Long> createReservation(@AuthUser AuthenticatedUser user, @RequestBody ReservationCreateRequest request) {
        Long reservationId = createReservationUseCase.createReservation(user, request);
        return ApiSuccessResponse.of(reservationId);
    }

    @PatchMapping("{reservationId}/cancel")
    public ApiSuccessResponse<Void> cancelReservation(
            @AuthUser AuthenticatedUser user,
            @PathVariable("reservationId") Long reservationId,
            @RequestBody ReservationCancelRequest request
    ) {
        cancelReservationUseCase.cancelReservation(user, reservationId, request);
        return ApiSuccessResponse.of();
    }

    @PostMapping("{reservationId}/approve")
    public ApiSuccessResponse<Void> approveReservation(
            @AuthUser AuthenticatedUser user,
            @PathVariable("reservationId") Long reservationId
    ) {
        approveReservationUseCase.approveReservation(user, reservationId);
        return ApiSuccessResponse.of();
    }

    @GetMapping("/{reservationId}")
    public ApiSuccessResponse<MyReservationSimpleResponse> getSimpleReservation(@AuthUser AuthenticatedUser user, @PathVariable Long reservationId) {
        return ApiSuccessResponse.of(queryUseCase.getSimpleReservation(user, reservationId));
    }

    @GetMapping("/{reservationId}/detail")
    public ApiSuccessResponse<MyReservationDetailResponse> getDetailReservation(@AuthUser AuthenticatedUser user, @PathVariable Long reservationId) {
        return ApiSuccessResponse.of(queryUseCase.getDetailReservation(user, reservationId));
    }

    @GetMapping("/my")
    public ApiSuccessResponse<MyReservationPageResponse> getMyReservations(
            @AuthUser AuthenticatedUser user,
            @RequestParam(name = "status", required = false) ReservationStatus status,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("modifiedAt").descending());
        return ApiSuccessResponse.of(queryUseCase.getMyReservations(user, pageable, status));
    }
}
