package com.breaditnow.customer.reservation.infrastructure;

import com.breaditnow.customer.reservation.domain.ReservationStatus;
import com.breaditnow.customer.reservation.domain.port.SaveReservationStatusHistoryPort;
import com.breaditnow.customer.reservation.infrastructure.jpa.JpaReservationStatusHistoryRepository;
import com.breaditnow.customer.reservation.infrastructure.jpa.entity.ReservationStatusHistoryEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ReservationStatusHistoryAdapter implements SaveReservationStatusHistoryPort {
    private final JpaReservationStatusHistoryRepository jpaReservationStatusHistoryRepository;

    @Override
    public void save(Long reservationId, ReservationStatus oldReservationStatus, ReservationStatus newReservationStatus) {
        ReservationStatusHistoryEntity entity = new ReservationStatusHistoryEntity(reservationId, oldReservationStatus, newReservationStatus);
        jpaReservationStatusHistoryRepository.save(entity);
    }
}
