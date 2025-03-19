package com.breaditnow.domain.domain.reservation.entity;

import static jakarta.persistence.GenerationType.*;
import static lombok.AccessLevel.*;

import com.breaditnow.domain.domain.product.entity.Product;
import com.breaditnow.domain.global.entity.BaseEntity;

import jakarta.persistence.Entity;
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
public class ReservationProduct extends BaseEntity {
	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "reservation_id", nullable = false)
	private Reservation reservation;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_id", nullable = false)
	private Product product;

	private Integer quantity;

	private Integer unitPrice;

	private String productName;

	private String productImage;

	@Builder
	public ReservationProduct(Reservation reservation, Product product, Integer quantity, Integer unitPrice,
		String productName,
		String productImage) {
		this.reservation = reservation;
		this.product = product;
		this.quantity = quantity;
		this.unitPrice = unitPrice;
		this.productName = productName;
		this.productImage = productImage;
	}

	public void setReservation(Reservation reservation) {
		this.reservation = reservation;
	}
}
