package com.breaditnow.auth.global.security.direct.provider;

import static com.breaditnow.auth.global.exception.AuthErrorCode.*;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.breaditnow.auth.global.security.AccountContext;
import com.breaditnow.auth.global.security.direct.token.JwtAuthenticationToken;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DirectAuthenticationProvider implements AuthenticationProvider {
	private final PasswordEncoder passwordEncoder;
	private final UserDetailsService userDetailsService;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String loginId = (String)authentication.getPrincipal();
		String password = (String)authentication.getCredentials();

		AccountContext accountContext = (AccountContext)userDetailsService.loadUserByUsername(loginId);

		if (!passwordEncoder.matches(password, accountContext.getPassword())) {
			throw new BadCredentialsException(INVALID_PASSWORD.name());
		}

		JwtAuthenticationToken jwtAuthenticationToken = new JwtAuthenticationToken(accountContext, null);
		jwtAuthenticationToken.setAuthenticated(true);
		return jwtAuthenticationToken;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.isAssignableFrom(JwtAuthenticationToken.class);
	}
}
