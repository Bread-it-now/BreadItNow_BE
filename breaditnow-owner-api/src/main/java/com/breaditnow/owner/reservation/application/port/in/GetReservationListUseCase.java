package com.breaditnow.owner.reservation.application.port.in;

import com.breaditnow.owner.reservation.application.dto.response.ReservationListResponse;
import org.springframework.data.domain.Pageable;

public interface GetReservationListUseCase {
    ReservationListResponse getReservationList(Long ownerId, Long bakeryId, String status, Pageable pageable);
}
