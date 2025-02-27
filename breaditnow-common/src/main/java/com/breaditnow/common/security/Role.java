package com.breaditnow.common.security;

import static com.breaditnow.common.exception.CommonErrorCode.*;

import com.breaditnow.common.exception.BreaditnowException;

public enum Role {
	CUSTOMER,
	OWNER;

	public static Role from(String role) {
		try {
			return Role.valueOf(role.toUpperCase());
		} catch (IllegalArgumentException e) {
			throw new BreaditnowException(ROLE_INVALID);
		}
	}
}
