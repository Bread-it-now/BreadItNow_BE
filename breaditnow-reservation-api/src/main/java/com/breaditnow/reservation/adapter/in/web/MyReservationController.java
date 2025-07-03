package com.breaditnow.reservation.adapter.in.web;

import com.breaditnow.common.domain.ReservationStatus;
import com.breaditnow.common.response.ApiSuccessResponse;
import com.breaditnow.reservation.adapter.in.resolver.AuthUser;
import com.breaditnow.reservation.adapter.in.resolver.AuthenticatedUser;
import com.breaditnow.reservation.application.dto.request.MyReservationCancelRequest;
import com.breaditnow.reservation.application.dto.request.MyReservationCreateRequest;
import com.breaditnow.reservation.application.dto.response.MyReservationDetailResponse;
import com.breaditnow.reservation.application.dto.response.MyReservationPageResponse;
import com.breaditnow.reservation.application.dto.response.MyReservationSimpleResponse;
import com.breaditnow.reservation.domain.port.in.MyReservationCancelUseCase;
import com.breaditnow.reservation.domain.port.in.MyReservationCreateUseCase;
import com.breaditnow.reservation.domain.port.in.MyReservationQueryUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static org.springframework.data.domain.Sort.Direction.DESC;

@RestController
@RequestMapping("/api/v1/my/reservation")
@RequiredArgsConstructor
public class MyReservationController {
    private final MyReservationCreateUseCase myReservationCreateUseCase;
    private final MyReservationCancelUseCase myReservationCancelUseCase;
    private final MyReservationQueryUseCase myReservationQueryUseCase;

    @PostMapping
    public ApiSuccessResponse<Map<String, Long>> createReservation(@AuthUser AuthenticatedUser user, @RequestBody MyReservationCreateRequest request) {
        Long reservationId = myReservationCreateUseCase.createReservation(user, request);
        return ApiSuccessResponse.of(Map.of("reservationId", reservationId));
    }

    @PostMapping("{reservationId}/cancel")
    public ApiSuccessResponse<Void> cancelReservation(
            @AuthUser AuthenticatedUser user,
            @PathVariable("reservationId") Long reservationId,
            @RequestBody MyReservationCancelRequest request
    ) {
        myReservationCancelUseCase.cancelReservation(user, reservationId, request);
        return ApiSuccessResponse.of();
    }

    @GetMapping
    public ApiSuccessResponse<MyReservationPageResponse> getMyReservations(
            @AuthUser AuthenticatedUser user,
            @RequestParam(name = "status", required = false) ReservationStatus status,
            @PageableDefault(sort = "modifiedAt", direction = DESC) Pageable pageable
    ) {
        return ApiSuccessResponse.of(myReservationQueryUseCase.getMyReservations(user, pageable, status));
    }

    @GetMapping("/{reservationId}")
    public ApiSuccessResponse<MyReservationSimpleResponse> getSimpleReservation(@AuthUser AuthenticatedUser user, @PathVariable Long reservationId) {
        return ApiSuccessResponse.of(myReservationQueryUseCase.getSimpleReservation(user, reservationId));
    }

    @GetMapping("/{reservationId}/detail")
    public ApiSuccessResponse<MyReservationDetailResponse> getDetailReservation(@AuthUser AuthenticatedUser user, @PathVariable Long reservationId) {
        return ApiSuccessResponse.of(myReservationQueryUseCase.getDetailReservation(user, reservationId));
    }
}
