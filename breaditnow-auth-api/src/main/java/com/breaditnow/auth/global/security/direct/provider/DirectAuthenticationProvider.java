package com.breaditnow.auth.global.security.direct.provider;

import static com.breaditnow.auth.global.exception.AuthErrorCode.*;
import static com.breaditnow.auth.global.security.Role.*;

import java.util.Map;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.breaditnow.auth.global.security.AccountContext;
import com.breaditnow.auth.global.security.direct.service.strategy.DirectUserDetailsService;
import com.breaditnow.auth.global.security.direct.token.JwtAuthenticationToken;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DirectAuthenticationProvider implements AuthenticationProvider {
	private final PasswordEncoder passwordEncoder;
	private final Map<String, DirectUserDetailsService> userDetailsServices;

	// private final CustomCustomerDetailsService customerDetailsService;
	// private final CustomOwnerDetailsService ownerDetailsService;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String loginId = (String)authentication.getPrincipal();
		String password = (String)authentication.getCredentials();

		String role = null;
		if (authentication instanceof JwtAuthenticationToken jwtToken) {
			role = jwtToken.getRole();
		}

		if (role == null) {
			role = CUSTOMER.name();
		} else {
			role = role.toUpperCase();
		}

		DirectUserDetailsService service = userDetailsServices.get(role);
		if (service == null) {
			throw new BadCredentialsException(ROLE_INVALID.name());
		}

		AccountContext accountContext = (AccountContext)service.loadUserByUsername(loginId);

		if (!passwordEncoder.matches(password, accountContext.getPassword())) {
			throw new BadCredentialsException(INVALID_PASSWORD.name());
		}

		JwtAuthenticationToken authenticatedToken = new JwtAuthenticationToken(
			accountContext,
			null,
			accountContext.getAuthorities(),
			role
		);
		authenticatedToken.setAuthenticated(true);
		return authenticatedToken;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.isAssignableFrom(JwtAuthenticationToken.class);
	}
}
