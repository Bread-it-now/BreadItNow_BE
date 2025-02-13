package com.breaditnow.mysql.domain.customer.entity;

import static jakarta.persistence.GenerationType.*;
import static lombok.AccessLevel.*;

import com.breaditnow.mysql.domain.customer.enumerate.Provider;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
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

	String email;

	String password;

	String nickname;

	String phone;

	@Enumerated(EnumType.STRING)
	Provider provider;

	String profileImage;

	@Builder(builderMethodName = "createCustomerBuilder")
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
