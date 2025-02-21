package com.breaditnow.auth.global.security.oauth2.handler;

import static com.breaditnow.auth.domain.token.domain.AuthTokenType.*;
import static com.breaditnow.auth.global.security.oauth2.cookie.CookieOAuth2AuthorizationRequestRepository.*;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import com.breaditnow.auth.domain.token.domain.AuthToken;
import com.breaditnow.auth.domain.token.repository.RedisTokenRepository;
import com.breaditnow.auth.global.security.AccountContext;
import com.breaditnow.auth.global.security.jwt.JwtTokenCreator;
import com.breaditnow.auth.global.security.oauth2.cookie.CookieOAuth2AuthorizationRequestRepository;
import com.breaditnow.common.util.CookieUtil;
import com.breaditnow.domain.customer.repository.CustomerRepository;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class Oauth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

	private final RedisTokenRepository redisTokenRepository;
	private final CookieUtil cookieUtil;
	private final CookieOAuth2AuthorizationRequestRepository
		cookieAuthorizationRequestRepository;
	private final CustomerRepository customerRepository;
	private final JwtTokenCreator jwtTokenCreator;

	@Value("${auth.token.refresh-cookie-key}")
	private String refreshCookieKey;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
		Authentication authentication) throws IOException {
		String targetUrl = determineTargetUrl(request, response, authentication);

		if (response.isCommitted()) {
			return;
		}

		// setRefreshTokenCookie(authentication, response);

		clearAuthenticationAttributes(request, response);
		getRedirectStrategy().sendRedirect(request, response, targetUrl);
	}

	protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response,
		Authentication authentication) {
		Optional<String> redirectUri = cookieUtil.getCookie(request, REDIRECT_URI_PARAM_COOKIE_NAME)
			.map(Cookie::getValue);
		String targetUrl = redirectUri.orElse(getDefaultTargetUrl());

		AccountContext accountContext = (AccountContext)authentication.getPrincipal();
		boolean isNewUser = customerRepository.existsById(accountContext.getUserId());

		return UriComponentsBuilder.
			fromUriString(targetUrl)
			.queryParam("isNewUser", isNewUser)
			.build().toUriString();
	}

	private void setRefreshTokenCookie(Authentication authentication, HttpServletResponse response) {
		AuthToken refreshToken = jwtTokenCreator.createToken(authentication, REFRESH);
		redisTokenRepository.saveToken(refreshToken);
		int maxAge = Math.toIntExact(refreshToken.expiresIn() / 1000);
		cookieUtil.addCookie(response, refreshCookieKey, refreshToken.token(), maxAge);
	}

	protected void clearAuthenticationAttributes(HttpServletRequest request, HttpServletResponse response) {
		super.clearAuthenticationAttributes(request);
		cookieAuthorizationRequestRepository.removeAuthorizationRequestCookies(request, response);
	}
}
