package com.breaditnow.auth.global.security.direct.provider;

import static com.breaditnow.auth.global.exception.AuthErrorCode.*;

import java.util.Map;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;

import com.breaditnow.auth.global.security.AccountContext;
import com.breaditnow.auth.global.security.direct.service.strategy.DirectUserDetailsService;
import com.breaditnow.auth.global.security.direct.token.JwtAuthenticationToken;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class DirectAuthenticationProvider implements AuthenticationProvider {
	private final PasswordEncoder passwordEncoder;
	private final Map<String, DirectUserDetailsService> userDetailsServices;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		JwtAuthenticationToken jwtToken = (JwtAuthenticationToken)authentication;

		final String loginId = (String)jwtToken.getPrincipal();
		final String password = (String)jwtToken.getCredentials();
		final String role = determineRole(jwtToken);

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
		log.info("accountContext ={}", accountContext);
		return authenticatedToken;
	}

	/**
	 * 인증 객체에서 role 정보를 추출합니다.
	 * 만약 role이 없거나 비어있으면 BadCredentialsException을 던집니다.
	 */
	private String determineRole(Authentication authentication) {
		JwtAuthenticationToken jwtToken = (JwtAuthenticationToken)authentication;
		String tokenRole = jwtToken.getRole();
		if (!StringUtils.hasText(tokenRole)) {
			throw new BadCredentialsException(ROLE_INVALID.name());
		}
		return tokenRole.toUpperCase();
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.isAssignableFrom(JwtAuthenticationToken.class);
	}
}
