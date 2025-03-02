package com.breaditnow.auth.global.security.direct.filter;

import static com.breaditnow.auth.global.exception.AuthErrorCode.*;
import static com.breaditnow.auth.global.security.jwt.token.AuthTokenType.*;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.StringUtils;

import com.breaditnow.auth.domain.auth.controller.req.LoginRequest;
import com.breaditnow.auth.global.exception.AuthException;
import com.breaditnow.auth.global.security.direct.token.JwtAuthenticationToken;
import com.breaditnow.auth.global.security.jwt.provider.JwtTokenCreator;
import com.breaditnow.auth.global.security.jwt.token.AuthToken;
import com.breaditnow.common.util.CookieUtil;
import com.breaditnow.common.util.JsonUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class DirectLoginFilter extends UsernamePasswordAuthenticationFilter {
	private final JwtTokenCreator jwtTokenCreator;
	private final CookieUtil cookieUtil;

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws
		AuthenticationException {

		LoginRequest loginRequest = JsonUtil.readValue(request, LoginRequest.class);

		if (!StringUtils.hasText(loginRequest.email()) || !StringUtils.hasText(loginRequest.password())) {
			throw new AuthException(MISSING_USERNAME_OR_PASSWORD);
		}

		JwtAuthenticationToken token = new JwtAuthenticationToken(loginRequest.email(), loginRequest.password());
		return getAuthenticationManager().authenticate(token);
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
		Authentication authentication) throws IOException, ServletException {

		AuthToken accessToken = jwtTokenCreator.createToken(authentication, ACCESS);
		AuthToken refreshToken = jwtTokenCreator.createToken(authentication, REFRESH);

		response.addHeader("Authorization", "Bearer " + accessToken);
		int maxAge = Math.toIntExact(refreshToken.expiresIn() / 1000);
		cookieUtil.addCookie(response, "refresh-token", refreshToken.token(), maxAge);

		super.successfulAuthentication(request, response, chain, authentication);
	}
}
