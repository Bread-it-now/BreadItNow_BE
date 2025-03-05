package com.breaditnow.auth.domain.auth.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.breaditnow.auth.domain.token.repository.AuthTokenRepository;
import com.breaditnow.auth.global.security.AccountContext;
import com.breaditnow.auth.global.security.jwt.provider.JwtTokenValidator;
import com.breaditnow.auth.global.security.jwt.token.AuthTokenType;
import com.breaditnow.common.util.CookieUtil;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class AuthSignOutService {
	private final CookieUtil cookieUtil;
	private final JwtTokenValidator jwtTokenValidator;
	private final AuthTokenRepository authTokenRepository;

	@Value("${auth.token.refresh-cookie-key}")
	private String refreshCookieKey;

	@Transactional
	public void logout(HttpServletRequest request, HttpServletResponse response) {
		String accessToken = jwtTokenValidator.resolveToken(request);
		String refreshToken = cookieUtil.getCookie(request, refreshCookieKey)
			.map(Cookie::getValue)
			.orElse(null);

		Optional<Authentication> authOpt = resolveAuthentication(accessToken, refreshToken);
		authOpt.ifPresent(authentication -> {
			AccountContext accountContext = (AccountContext)authentication.getPrincipal();
			authTokenRepository.deleteToken(AuthTokenType.REFRESH, accountContext.getRole(),
				accountContext.getUserId());
		});

		response.setHeader("Authorization", "");
		cookieUtil.deleteCookie(request, response, refreshCookieKey);
	}

	private Optional<Authentication> resolveAuthentication(String accessToken, String refreshToken) {
		if (StringUtils.hasText(accessToken)) {
			return Optional.of(jwtTokenValidator.getAuthentication(accessToken));
		} else if (StringUtils.hasText(refreshToken)) {
			return Optional.of(jwtTokenValidator.getAuthentication(refreshToken));
		}
		return Optional.empty();
	}
}
