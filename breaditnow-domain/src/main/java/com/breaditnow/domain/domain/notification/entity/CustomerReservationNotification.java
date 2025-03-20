package com.breaditnow.domain.domain.notification.entity;

import static jakarta.persistence.FetchType.*;

import com.breaditnow.domain.domain.customer.entity.Customer;
import com.breaditnow.domain.domain.reservation.entity.Reservation;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;

@Entity
@DiscriminatorValue("reservation")
@Getter
public class CustomerReservationNotification extends CustomerNotification {
	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "reservation_id")
	private Reservation reservation;

	public CustomerReservationNotification(Customer customer, Reservation reservation) {
		super(customer, true, false);
		this.reservation = reservation;
	}
}
