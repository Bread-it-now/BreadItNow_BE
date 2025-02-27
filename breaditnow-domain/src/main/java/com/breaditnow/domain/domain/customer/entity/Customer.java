package com.breaditnow.domain.domain.customer.entity;

import static jakarta.persistence.GenerationType.*;
import static lombok.AccessLevel.*;

import java.time.LocalDateTime;

import com.breaditnow.domain.domain.alert.entity.CustomerAlertSetting;
import com.breaditnow.domain.domain.customer.enumerate.Provider;

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
	private Provider provider;

	private String oauth2Id;

	@Column(unique = true)
	private String email;

	private String password;

	@Column(unique = true)
	private String nickname;

	private String phone;

	private String profileImage;

	@OneToOne(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
	private CustomerAlertSetting alertSetting;

	private LocalDateTime lastLoginAt;

	@Builder
	public Customer(Long id, Provider provider, String oauth2Id, String email, String password,
		LocalDateTime lastLoginAt) {
		this.id = id;
		this.provider = provider;
		this.oauth2Id = oauth2Id;
		this.email = email;
		this.password = password;
		this.lastLoginAt = LocalDateTime.now();
	}

	public void updateLastLoginAt() {
		this.lastLoginAt = LocalDateTime.now();
	}

	public boolean isFirstLogin() {
		return this.nickname == null;
	}
}
