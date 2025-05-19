package com.breaditnow.domain.domain.favorite.entity;

import static jakarta.persistence.GenerationType.*;
import static lombok.AccessLevel.*;

import com.breaditnow.domain.domain.bakery.entity.Bakery;
import com.breaditnow.domain.domain.customer.entity.Customer;
import com.breaditnow.domain.global.entity.BaseEntity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
@Table(name = "P_CustomerBakeryFavorite")
public class CustomerBakeryFavorite extends BaseEntity {
	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "customer_id", nullable = false)
	private Customer customer;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "bakery_id", nullable = false)
	private Bakery bakery;

	private boolean isActive = true;

	@Builder
	public CustomerBakeryFavorite(Customer customer, Bakery bakery) {
		this.customer = customer;
		this.bakery = bakery;
	}

	public void changeActive(boolean isActive) {
		this.isActive = isActive;
	}
}
