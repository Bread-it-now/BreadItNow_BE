package com.breaditnow.domain.domain.reservation.entity;

import static com.breaditnow.domain.domain.reservation.enumerate.ReservationStatus.CANCELLED;
import static jakarta.persistence.GenerationType.*;
import static java.util.UUID.randomUUID;
import static lombok.AccessLevel.*;

import java.time.LocalDateTime;

import com.breaditnow.domain.domain.bakery.entity.Bakery;
import com.breaditnow.domain.domain.customer.entity.Customer;
import com.breaditnow.domain.domain.reservation.enumerate.ReservationStatus;
import com.breaditnow.domain.global.entity.BaseEntity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Reservation extends BaseEntity {
	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "customer_id", nullable = false)
	private Customer customer;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "bakery_id", nullable = false)
	private Bakery bakery;

	@Enumerated(EnumType.STRING)
	private ReservationStatus status;

	private int totalPrice;

	private String cancelReason;

	private LocalDateTime pickupDeadline;

	@Column(unique = true, nullable = false)
	private String reservationNumber;

	@Builder
	public Reservation(Customer customer, Bakery bakery, ReservationStatus status, int totalPrice,
					   LocalDateTime pickupDeadline) {
		this.customer = customer;
		this.bakery = bakery;
		this.status = status;
		this.totalPrice = totalPrice;
		this.pickupDeadline = pickupDeadline;
		this.reservationNumber = generateReservationNumber();
	}

	private String generateReservationNumber() {
		return randomUUID().toString().substring(0, 6).toUpperCase();
	}

	public void updateStatus(ReservationStatus status) {
		this.status = status;
	}

	public void updateCancelReason(String reason) {
		this.cancelReason = reason;
	}
}
