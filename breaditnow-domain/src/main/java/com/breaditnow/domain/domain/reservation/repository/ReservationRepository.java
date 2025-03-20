package com.breaditnow.domain.domain.reservation.repository;

import com.breaditnow.domain.domain.reservation.enumerate.ReservationRequestStatus;
import com.breaditnow.domain.domain.reservation.enumerate.ReservationStatus;
import com.breaditnow.domain.global.exception.DomainException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.breaditnow.domain.domain.reservation.entity.Reservation;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import static com.breaditnow.domain.global.exception.DomainErrorCode.RESERVATION_NOT_FOUND;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    default Reservation getById(Long id) {
        return findById(id)
                .orElseThrow(() -> new DomainException(RESERVATION_NOT_FOUND));
    }

    @Query("SELECT r FROM Reservation r WHERE r.customer.id = :customerId AND (:status IS NULL OR r.status = :status)")
    Page<Reservation> findByCustomerIdAndOptionalStatus(@Param("customerId") Long customerId, @Param("status") ReservationStatus status, Pageable pageable);

    default Page<Reservation> getReservationsByStatus(Long customerId, ReservationRequestStatus status, Pageable pageable) {
        return findByCustomerIdAndOptionalStatus(customerId, ReservationRequestStatus.toReservationStatus(status), pageable);
    }
}

