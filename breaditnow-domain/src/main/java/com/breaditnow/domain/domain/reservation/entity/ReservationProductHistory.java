package com.breaditnow.domain.domain.reservation.entity;

import static jakarta.persistence.FetchType.*;
import static jakarta.persistence.GenerationType.*;
import static lombok.AccessLevel.*;

import com.breaditnow.domain.global.entity.BaseEntity;

import jakarta.persistence.Entity;
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
public class ReservationProductHistory extends BaseEntity {
	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Long id;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "reservation_id", nullable = false)
	private Reservation reservation;

	private String productName;

	private Integer productPrice;

	@Builder
	public ReservationProductHistory(Reservation reservation, String productName, Integer productPrice) {
		this.reservation = reservation;
		this.productName = productName;
		this.productPrice = productPrice;
	}

	public void setReservation(Reservation reservation) {
		this.reservation = reservation;
	}
}
