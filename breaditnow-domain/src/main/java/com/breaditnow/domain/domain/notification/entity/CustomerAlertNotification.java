package com.breaditnow.domain.domain.notification.entity;

import static jakarta.persistence.GenerationType.*;
import static lombok.AccessLevel.*;

import com.breaditnow.domain.domain.customer.entity.Customer;
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
public class CustomerAlertNotification extends BaseEntity {
	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "customer_id", nullable = false)
	private Customer customer;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_id")
	private Product product;

	private Integer remainingCount;

	private Integer alertCount;

	private String bakeryName;

	private String productName;

	private Boolean isRead;

	@Builder
	public CustomerAlertNotification(Customer customer, Product product, Integer remainingCount, Integer alertCount,
		String bakeryName, String productName, Boolean isRead) {
		this.customer = customer;
		this.product = product;
		this.remainingCount = remainingCount;
		this.alertCount = alertCount;
		this.bakeryName = bakeryName;
		this.productName = productName;
		this.isRead = isRead;
	}
}
