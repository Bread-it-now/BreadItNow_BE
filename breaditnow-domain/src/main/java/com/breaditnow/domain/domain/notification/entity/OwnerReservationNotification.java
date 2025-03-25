package com.breaditnow.domain.domain.notification.entity;

import static jakarta.persistence.FetchType.*;
import static jakarta.persistence.GenerationType.*;
import static lombok.AccessLevel.*;

import com.breaditnow.domain.domain.owner.entity.Owner;
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
public class OwnerReservationNotification extends BaseEntity {
	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Long id;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "owner_id", nullable = false)
	private Owner owner;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "reservation_id")
	private Reservation reservation;

	private boolean isActive = true;
	private boolean isRead;

	@Builder
	public OwnerReservationNotification(Owner owner, Reservation reservation, boolean isActive, boolean isRead) {
		this.owner = owner;
		this.reservation = reservation;
		this.isActive = isActive;
		this.isRead = isRead;
	}
	
	public void changeIsRead(boolean isRead) {
		this.isRead = isRead;
	}

	public void changeIsActive(boolean isActive) {
		this.isActive = isActive;
	}
}
