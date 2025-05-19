package com.breaditnow.domain.domain.reservation.entity;

import static jakarta.persistence.EnumType.*;
import static jakarta.persistence.FetchType.*;
import static jakarta.persistence.GenerationType.*;
import static java.util.UUID.*;
import static lombok.AccessLevel.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
@Table(name = "P_Reservation")
public class Reservation extends BaseEntity {
	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Long id;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "customer_id", nullable = false)
	private Customer customer;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "bakery_id", nullable = false)
	private Bakery bakery;

	@Enumerated(STRING)
	private ReservationStatus status;

	@OneToMany(mappedBy = "reservation")
	private List<ReservationProduct> reservationProducts = new ArrayList<>();

	private Integer totalPrice;

	private String cancelReason;

	private LocalDateTime pickupDeadline;

	@Column(nullable = false)
	private Integer reservationNumber;

	@Builder
	public Reservation(Customer customer, Bakery bakery, ReservationStatus status, Integer totalPrice,
		LocalDateTime pickupDeadline, Integer reservationNumber) {
		this.customer = customer;
		this.bakery = bakery;
		this.status = status;
		this.totalPrice = totalPrice;
		this.pickupDeadline = pickupDeadline;
		this.reservationNumber = reservationNumber;
	}

	public void updateStatus(ReservationStatus status) {
		this.status = status;
	}

	public void updateCancelReason(String reason) {
		this.cancelReason = reason;
	}

	public void addReservationProduct(ReservationProduct reservationProduct) {
		this.reservationProducts.add(reservationProduct);
		reservationProduct.setReservation(this);
	}
}
