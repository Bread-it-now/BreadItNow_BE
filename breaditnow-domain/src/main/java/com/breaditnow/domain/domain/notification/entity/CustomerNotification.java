package com.breaditnow.domain.domain.notification.entity;

import static jakarta.persistence.GenerationType.*;
import static lombok.AccessLevel.*;

import com.breaditnow.domain.domain.customer.entity.Customer;
import com.breaditnow.domain.domain.notification.enumerate.CustomerNotificationType;
import com.breaditnow.domain.domain.product.entity.Product;
import com.breaditnow.domain.domain.reservation.entity.Reservation;
import com.breaditnow.domain.global.entity.BaseEntity;

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
public class CustomerNotification extends BaseEntity {
	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "customer_id", nullable = false)
	private Customer customer;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "reservation_id")
	private Reservation reservation;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_id")
	private Product product;

	@Enumerated(EnumType.STRING)
	private CustomerNotificationType type;

	private String content;

	private boolean isRead;

	@Builder
	public CustomerNotification(Customer customer, Reservation reservation, Product product,
		CustomerNotificationType type,
		String content, boolean isRead) {
		this.customer = customer;
		this.reservation = reservation;
		this.product = product;
		this.type = type;
		this.content = content;
		this.isRead = isRead;
	}
}
