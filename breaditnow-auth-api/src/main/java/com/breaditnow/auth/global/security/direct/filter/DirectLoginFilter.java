package com.breaditnow.auth.global.security.direct.filter;

import static com.breaditnow.auth.global.exception.AuthErrorCode.*;

import java.io.IOException;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.StringUtils;

import com.breaditnow.auth.domain.auth.controller.req.LoginRequest;
import com.breaditnow.auth.global.security.direct.token.JwtAuthenticationToken;
import com.breaditnow.common.util.JsonUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DirectLoginFilter extends UsernamePasswordAuthenticationFilter {

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws
		AuthenticationException {

		LoginRequest loginRequest = JsonUtil.readValue(request, LoginRequest.class);

		if (!StringUtils.hasText(loginRequest.email()) || !StringUtils.hasText(loginRequest.password())) {
			throw new AuthenticationServiceException(LOGIN_FAILED.name());
		}

		JwtAuthenticationToken token = new JwtAuthenticationToken(
			loginRequest.email(),
			loginRequest.password(),
			loginRequest.role()
		);

		return getAuthenticationManager().authenticate(token);
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
		Authentication authentication) throws IOException, ServletException {
		super.successfulAuthentication(request, response, chain, authentication);
	}
}
