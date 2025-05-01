package com.breaditnow.auth.global.security.direct.token;

import java.util.Collection;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import lombok.Getter;

public class JwtAuthenticationToken extends AbstractAuthenticationToken {
	private final Object principal;
	private final Object credentials;
	@Getter
	private final String role;

	public JwtAuthenticationToken(Object principal, Object credentials, String role) {
		super(null);
		this.principal = principal;
		this.credentials = credentials;
		this.role = role;
		setAuthenticated(false);
	}

	public JwtAuthenticationToken(Object principal, Object credentials,
		Collection<? extends GrantedAuthority> authorities, String role) {
		super(authorities);
		this.principal = principal;
		this.credentials = credentials;
		this.role = role;
		setAuthenticated(true);
	}

	@Override
	public Object getCredentials() {
		return this.credentials;
	}

	@Override
	public Object getPrincipal() {
		return this.principal;
	}

}
