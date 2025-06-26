package com.breaditnow.reservation.infrastructure.adapter.in.presentation;

import com.breaditnow.common.domain.ReservationStatus;
import com.breaditnow.common.response.ApiSuccessResponse;
import com.breaditnow.common.security.annotation.AuthOwner;
import com.breaditnow.reservation.application.dto.response.ReservationListResponse;
import com.breaditnow.reservation.application.port.in.GetReservationListUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/reservation/bakery/{bakeryId}")
public class OwnerReservationController {

    private final GetReservationListUseCase getReservationListUseCase;

    @GetMapping
    public ResponseEntity<ApiSuccessResponse<ReservationListResponse>> getReservations(
            @AuthOwner Long ownerId,
            @PathVariable Long bakeryId,
            @RequestParam(required = false) ReservationStatus status,
            Pageable pageable
    ) {
        ReservationListResponse responseData = getReservationListUseCase.getReservationList(ownerId, bakeryId, status, pageable);
        return ResponseEntity.ok(ApiSuccessResponse.of(responseData));
    }
}

