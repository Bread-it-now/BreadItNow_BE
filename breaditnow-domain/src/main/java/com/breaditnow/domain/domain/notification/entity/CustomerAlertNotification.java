package com.breaditnow.domain.domain.notification.entity;

import static jakarta.persistence.FetchType.*;
import static lombok.AccessLevel.*;

import com.breaditnow.domain.domain.customer.entity.Customer;
import com.breaditnow.domain.domain.product.entity.Product;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("alert")
@NoArgsConstructor(access = PROTECTED)
@Getter
public class CustomerAlertNotification extends CustomerNotification {
	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "product_id")
	private Product product;

	private Integer remainingCount;

	private Integer alertCount;

	private String bakeryName;

	private String productName;

	@Builder
	public CustomerAlertNotification(Customer customer, Product product, Integer remainingCount, Integer alertCount,
		String bakeryName, String productName) {
		super(customer, true, false);
		this.product = product;
		this.remainingCount = remainingCount;
		this.alertCount = alertCount;
		this.bakeryName = bakeryName;
		this.productName = productName;
	}
}
