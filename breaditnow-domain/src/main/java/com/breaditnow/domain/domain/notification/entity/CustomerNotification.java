package com.breaditnow.domain.domain.notification.entity;

import static jakarta.persistence.DiscriminatorType.*;
import static jakarta.persistence.FetchType.*;
import static jakarta.persistence.GenerationType.*;
import static jakarta.persistence.InheritanceType.*;
import static lombok.AccessLevel.*;

import com.breaditnow.domain.domain.customer.entity.Customer;
import com.breaditnow.domain.global.entity.BaseEntity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = PROTECTED)
@Inheritance(strategy = SINGLE_TABLE)
@DiscriminatorColumn(name = "notification_type", discriminatorType = STRING)
@Getter
@Table(name = "P_CustomerNotification")
public class CustomerNotification extends BaseEntity {
	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Long id;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "customer_id", nullable = false)
	private Customer customer;

	@Column(name = "notification_type", insertable = false, updatable = false)
	private String notificationType;

	private boolean isActive = true;
	private boolean isRead;

	public CustomerNotification(Customer customer, boolean isActive, boolean isRead) {
		this.customer = customer;
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
