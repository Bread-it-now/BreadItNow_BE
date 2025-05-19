package com.breaditnow.domain.domain.owner.entity;

import static jakarta.persistence.GenerationType.*;
import static lombok.AccessLevel.*;

import com.breaditnow.domain.global.entity.BaseEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
@ToString
@Table(name = "P_Owner")
public class Owner extends BaseEntity {
	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Long id;

	String email;

	String password;

	private String fcmToken;

	@Builder
	public Owner(String email, String password, String fcmToken) {
		this.email = email;
		this.password = password;
		this.fcmToken = fcmToken;
	}

	public void changeFcmToken(String token) {
		this.fcmToken = token;
	}

	public void changePassword(String newPassword) {
		this.password = newPassword;
	}
}
