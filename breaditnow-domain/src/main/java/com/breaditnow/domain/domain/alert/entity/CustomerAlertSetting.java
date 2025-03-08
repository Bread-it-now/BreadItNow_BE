package com.breaditnow.domain.domain.alert.entity;

import static jakarta.persistence.GenerationType.*;
import static lombok.AccessLevel.*;

import com.breaditnow.domain.domain.customer.entity.Customer;
import com.breaditnow.domain.global.entity.BaseEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class CustomerAlertSetting extends BaseEntity {
	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Long id;

	@OneToOne
	@JoinColumn(name = "customer_id", nullable = false)
	private Customer customer;

	private boolean isActive = false;

	private String monTime;
	private String tueTime;
	private String wedTime;
	private String thuTime;
	private String friTime;
	private String satTime;
	private String sunTime;
}
