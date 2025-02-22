package com.breaditnow.auth.global.security;

import static com.breaditnow.auth.global.security.Role.*;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class AccountContext implements UserDetails, OAuth2User {

	private Long userId;
	private Role role;

	private String email;
	private String password;

	private String oauth2Id;
	private Map<String, Object> attributes;

	public static AccountContext ofOAuth2(Long userId, String oauth2Id, Map<String, Object> attributes) {
		return AccountContext.builder()
			.userId(userId)
			.oauth2Id(oauth2Id)
			.attributes(attributes)
			.role(CUSTOMER)
			.build();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + role.name()));
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public String getUsername() {
		return this.email != null ? this.email : this.oauth2Id;
	}

	@Override
	public Map<String, Object> getAttributes() {
		return this.attributes;
	}

	@Override
	public String getName() {
		return this.oauth2Id;
	}
}
