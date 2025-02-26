package com.breaditnow.domain.domain.owner.entity;

import static jakarta.persistence.GenerationType.*;
import static lombok.AccessLevel.*;

import jakarta.persistence.Entity;
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
public class Owner {
	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Long id;

	String email;

	String password;

	@Builder
	public Owner(String email, String password) {
		this.email = email;
		this.password = password;
	}
}
