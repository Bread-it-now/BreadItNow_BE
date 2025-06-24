package com.breaditnow.reservation.infrastructure.adapter.out.persistence;

import com.breaditnow.reservation.application.port.out.ReservationRepositoryPort;
import com.breaditnow.reservation.domain.Reservation;
import com.breaditnow.reservation.infrastructure.jpa.entity.ReservationEntity;
import com.breaditnow.reservation.infrastructure.jpa.repository.JpaReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ReservationPersistenceAdapter implements ReservationRepositoryPort {
    private final JpaReservationRepository jpaRepository;

    @Override
    public Optional<Reservation> findById(Long reservationId) {
        return jpaRepository.findById(reservationId)
                .map(ReservationEntity::toDomain);
    }

    @Override
    public Reservation save(Reservation reservation) {
        ReservationEntity entity = ReservationEntity.from(reservation);
        ReservationEntity savedEntity = jpaRepository.save(entity);
        return savedEntity.toDomain();
    }
}
