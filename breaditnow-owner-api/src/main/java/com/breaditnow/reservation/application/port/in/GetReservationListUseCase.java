package com.breaditnow.reservation.application.port.in;

import com.breaditnow.common.domain.ReservationStatus;
import com.breaditnow.reservation.application.dto.response.ReservationListResponse;
import org.springframework.data.domain.Pageable;

public interface GetReservationListUseCase {
    ReservationListResponse getReservationList(Long ownerId, Long bakeryId, ReservationStatus status, Pageable pageable);
}
