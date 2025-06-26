package com.breaditnow.owner.reservation.infrastructure.adapter.in.presentation;

import com.breaditnow.common.response.ApiSuccessResponse;
import com.breaditnow.owner.common.security.annotation.AuthOwner;
import com.breaditnow.owner.reservation.application.dto.response.ReservationListResponse;
import com.breaditnow.owner.reservation.application.port.in.GetReservationListUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/reservation/bakery/{bakeryId}")
@RequiredArgsConstructor
public class OwnerReservationController {

    private final GetReservationListUseCase getReservationListUseCase;

    @GetMapping
    public ResponseEntity<ApiSuccessResponse<ReservationListResponse>> getReservations(
            @AuthOwner Long ownerId,
            @PathVariable Long bakeryId,
            @RequestParam(defaultValue = "ALL") String status,
            Pageable pageable
    ) {
        ReservationListResponse responseData = getReservationListUseCase.getReservationList(ownerId, bakeryId, status, pageable);
        return ResponseEntity.ok(ApiSuccessResponse.of(responseData));
    }
}
