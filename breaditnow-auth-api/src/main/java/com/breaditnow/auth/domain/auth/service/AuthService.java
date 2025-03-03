package com.breaditnow.auth.domain.auth.service;

import static java.util.stream.Collectors.*;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.breaditnow.auth.domain.auth.controller.req.SignupRequest;
import com.breaditnow.auth.domain.auth.service.strategy.SignupStrategy;
import com.breaditnow.auth.domain.token.repository.AuthTokenRepository;
import com.breaditnow.auth.global.security.AccountContext;
import com.breaditnow.auth.global.security.Role;
import com.breaditnow.auth.global.security.jwt.provider.JwtTokenValidator;
import com.breaditnow.auth.global.security.jwt.token.AuthTokenType;
import com.breaditnow.common.util.CookieUtil;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {

	private final CookieUtil cookieUtil;
	private final JwtTokenValidator jwtTokenValidator;
	private final AuthTokenRepository authTokenRepository;
	private final Map<Role, SignupStrategy> signupStrategies;

	@Autowired
	public AuthService(JwtTokenValidator jwtTokenValidator, CookieUtil cookieUtil,
		AuthTokenRepository authTokenRepository, List<SignupStrategy> strategies) {
		this.jwtTokenValidator = jwtTokenValidator;
		this.cookieUtil = cookieUtil;
		this.authTokenRepository = authTokenRepository;
		this.signupStrategies = strategies.stream()
			.collect(toMap(SignupStrategy::getRole, strategy -> strategy));
	}

	@Transactional
	public Long signup(SignupRequest signupRequest) {
		Role role = Role.from(signupRequest.role());
		SignupStrategy strategy = signupStrategies.get(role);
		return strategy.signup(signupRequest);
	}

	@Transactional
	public void logout(HttpServletRequest request, HttpServletResponse response) {
		String accessToken = jwtTokenValidator.resolveToken(request);
		String refreshToken = cookieUtil.getCookie(request, "refreshToken")
			.map(Cookie::getValue)
			.orElse(null);

		Authentication authentication;
		if (accessToken != null) {
			authentication = jwtTokenValidator.getAuthentication(accessToken);
		} else {
			authentication = jwtTokenValidator.getAuthentication(refreshToken);
		}
		AccountContext accountContext = (AccountContext)authentication.getPrincipal();
		authTokenRepository.deleteToken(AuthTokenType.REFRESH, accountContext.getUserId());

		response.setHeader("Authorization", "");
		cookieUtil.deleteCookie(request, response, "refreshToken");
	}
}
