package com.breaditnow.auth.global.security.direct.provider;

import static com.breaditnow.auth.global.exception.AuthErrorCode.*;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.breaditnow.auth.global.security.AccountContext;
import com.breaditnow.auth.global.security.direct.service.CustomCustomerDetailsService;
import com.breaditnow.auth.global.security.direct.service.CustomOwnerDetailsService;
import com.breaditnow.auth.global.security.direct.token.JwtAuthenticationToken;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DirectAuthenticationProvider implements AuthenticationProvider {
	private final PasswordEncoder passwordEncoder;
	private final CustomCustomerDetailsService customerDetailsService;
	private final CustomOwnerDetailsService ownerDetailsService;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String loginId = (String)authentication.getPrincipal();
		String password = (String)authentication.getCredentials();

		String role = null;
		if (authentication instanceof JwtAuthenticationToken jwtToken) {
			role = jwtToken.getRole();
		}

		AccountContext accountContext;
		if ("OWNER".equalsIgnoreCase(role)) {
			accountContext = (AccountContext)ownerDetailsService.loadUserByUsername(loginId);
		} else {
			accountContext = (AccountContext)customerDetailsService.loadUserByUsername(loginId);
		}

		if (!passwordEncoder.matches(password, accountContext.getPassword())) {
			throw new BadCredentialsException(INVALID_PASSWORD.name());
		}

		JwtAuthenticationToken authenticatedToken = new JwtAuthenticationToken(accountContext, null,
			accountContext.getAuthorities(), role);
		authenticatedToken.setAuthenticated(true);

		return authenticatedToken;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.isAssignableFrom(JwtAuthenticationToken.class);
	}
}
