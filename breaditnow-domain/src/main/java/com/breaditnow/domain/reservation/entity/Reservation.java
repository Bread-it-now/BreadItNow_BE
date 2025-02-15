package com.breaditnow.domain.reservation.entity;

import static jakarta.persistence.GenerationType.*;
import static lombok.AccessLevel.*;

import java.time.LocalDateTime;

import com.breaditnow.domain.bakery.entity.Bakery;
import com.breaditnow.domain.customer.entity.Customer;
import com.breaditnow.domain.reservation.enumerate.ReservationStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Reservation {
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

	private LocalDateTime pickupDeadline;

	@Builder
	public Reservation(Customer customer, Bakery bakery, ReservationStatus status, int totalPrice,
		LocalDateTime pickupDeadline) {
		this.customer = customer;
		this.bakery = bakery;
		this.status = status;
		this.totalPrice = totalPrice;
		this.pickupDeadline = pickupDeadline;
	}
}
