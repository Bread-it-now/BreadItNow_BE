//package com.breaditnow.auth_tmp.global.security;
//
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.util.StringUtils;
//
//import com.breaditnow.common.exception.AuthException;
//
//public enum Role {
//	CUSTOMER,
//	OWNER;
//
//	public static Role from(String role) {
//		if (!StringUtils.hasText(role)) {
//			throw new AuthException(ROLE_INVALID);
//		}
//
//		String normalizedRole = role.trim().toUpperCase();
//		try {
//			return Role.valueOf(normalizedRole);
//		} catch (IllegalArgumentException e) {
//			throw new AuthException(ROLE_INVALID);
//		}
//	}
//
//	public static String toAuthority(Role role) {
//		if (role == null) {
//			throw new BadCredentialsException(ROLE_INVALID.name());
//		}
//		return "ROLE_" + role.name();
//	}
//}
