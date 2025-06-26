package com.breaditnow.reservation.adapter.out.persistence;

import com.breaditnow.reservation.domain.port.out.ReservationRepository;
import com.breaditnow.reservation.domain.model.Reservation;
import com.breaditnow.reservation.adapter.out.persistence.entity.ReservationEntity;
import com.breaditnow.reservation.adapter.out.persistence.repository.JpaReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ReservationPersistenceAdapter implements ReservationRepository {
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
