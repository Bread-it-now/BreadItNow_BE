package com.breaditnow.domain.domain.favorite.entity;

import static jakarta.persistence.FetchType.*;
import static jakarta.persistence.GenerationType.*;
import static lombok.AccessLevel.*;

import com.breaditnow.domain.domain.customer.entity.Customer;
import com.breaditnow.domain.domain.product.entity.Product;
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
public class CustomerProductFavorite extends BaseEntity {
	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Long id;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "customer_id", nullable = false)
	private Customer customer;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "product_id", nullable = false)
	private Product product;

	private boolean isActive = true;

	@Builder
	public CustomerProductFavorite(Customer customer, Product product) {
		this.customer = customer;
		this.product = product;
	}

	public void changeActive(boolean isActive) {
		this.isActive = isActive;
	}
}
