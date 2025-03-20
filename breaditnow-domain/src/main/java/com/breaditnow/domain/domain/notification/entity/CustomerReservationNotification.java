package com.breaditnow.domain.domain.notification.entity;

import static jakarta.persistence.FetchType.*;
import static jakarta.persistence.GenerationType.*;
import static lombok.AccessLevel.*;

import com.breaditnow.domain.domain.customer.entity.Customer;
import com.breaditnow.domain.domain.reservation.entity.Reservation;
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
public class CustomerReservationNotification extends BaseEntity {
	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Long id;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "customer_id", nullable = false)
	private Customer customer;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "reservation_id")
	private Reservation reservation;

	private boolean isRead;
	private boolean isActive = true;

	@Builder
	public CustomerReservationNotification(Customer customer, Reservation reservation, boolean isRead) {
		this.customer = customer;
		this.reservation = reservation;
		this.isRead = isRead;
	}

	public void changeIsRead(boolean isRead) {
		this.isRead = isRead;
	}

	public void changeIsActive(boolean isActive) {
		this.isActive = isActive;
	}
}
