package com.breaditnow.domain.domain.notification.entity;

import static jakarta.persistence.FetchType.*;
import static lombok.AccessLevel.*;

import com.breaditnow.domain.domain.customer.entity.Customer;
import com.breaditnow.domain.domain.reservation.entity.Reservation;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("reservation")
@NoArgsConstructor(access = PROTECTED)
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
