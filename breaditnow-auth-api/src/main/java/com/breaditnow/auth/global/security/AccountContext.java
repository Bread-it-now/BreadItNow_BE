package com.breaditnow.auth.global.security;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import lombok.Builder;
import lombok.Getter;

@Getter
public class AccountContext implements UserDetails, OAuth2User {

	private Long userId;
	private Role role;

	private String email;
	private String password;

	private String oauth2Id;
	private Map<String, Object> attributes;

	@Builder(builderMethodName = "emailLoginBuilder")
	public AccountContext(Long userId, String email, String password, Role role) {
		this.userId = userId;
		this.email = email;
		this.password = password;
		this.role = role;
	}

	@Builder(builderMethodName = "oauth2LoginBuilder")
	public AccountContext(Long userId, String oauth2Id, Map<String, Object> attributes, Role role) {
		this.userId = userId;
		this.oauth2Id = oauth2Id;
		this.attributes = attributes;
		this.role = role;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + role.name()));
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return email != null ? email : oauth2Id;
	}

	@Override
	public Map<String, Object> getAttributes() {
		return attributes;
	}

	@Override
	public String getName() {
		return getUsername();
	}
}
