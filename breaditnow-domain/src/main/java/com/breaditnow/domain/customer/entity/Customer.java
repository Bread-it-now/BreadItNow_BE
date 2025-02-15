package com.breaditnow.domain.customer.entity;

import static jakarta.persistence.GenerationType.*;
import static lombok.AccessLevel.*;

import com.breaditnow.domain.alert.entity.CustomerAlertSetting;
import com.breaditnow.domain.customer.enumerate.Provider;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
@ToString
public class Customer {
	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Long id;

	@Enumerated(EnumType.STRING)
	Provider provider;

	@Column(nullable = false, unique = true)
	String email;

	@Column(nullable = false)
	String password;

	@Column(nullable = false, unique = true)
	String nickname;

	String phone;

	String profileImage;

	@OneToOne(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
	private CustomerAlertSetting alertSetting;

	@Builder
	public Customer(String email, String password, String nickname, String phone, Provider provider,
		String profileImage) {
		this.email = email;
		this.password = password;
		this.nickname = nickname;
		this.phone = phone;
		this.provider = provider;
		this.profileImage = profileImage;
	}
}
