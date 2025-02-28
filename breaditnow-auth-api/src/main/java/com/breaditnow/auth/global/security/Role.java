package com.breaditnow.auth.global.security;

import static com.breaditnow.auth.global.exception.AuthErrorCode.*;

import com.breaditnow.auth.global.exception.AuthException;

public enum Role {
	CUSTOMER,
	OWNER;

	public static Role from(String role) {
		try {
			return Role.valueOf(role.toUpperCase());
		} catch (IllegalArgumentException e) {
			throw new AuthException(ROLE_INVALID);
		}
	}
}
