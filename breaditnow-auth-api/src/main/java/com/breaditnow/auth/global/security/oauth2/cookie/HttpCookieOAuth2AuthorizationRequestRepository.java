package com.breaditnow.auth.global.security.oauth2.cookie;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.stereotype.Component;

import com.breaditnow.common.util.CookieUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class HttpCookieOAuth2AuthorizationRequestRepository
	implements AuthorizationRequestRepository<OAuth2AuthorizationRequest> {

	public static final String OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME = "oauth2_auth_request";
	public static final String REDIRECT_URL_PARAM_COOKIE_NAME = "redirect_url";
	private static final int cookieExpireSeconds = 180;

	private final CookieUtil cookieUtil;

	@Override
	public OAuth2AuthorizationRequest loadAuthorizationRequest(HttpServletRequest request) {
		return cookieUtil.resolveCookie(request, OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME)
			.map(cookie -> cookieUtil.deserialize(cookie, OAuth2AuthorizationRequest.class))
			.orElse(null);
	}

	@Override
	public void saveAuthorizationRequest(
		OAuth2AuthorizationRequest authorizationRequest,
		HttpServletRequest request,
		HttpServletResponse response) {
		if (authorizationRequest == null) {
			cookieUtil.deleteCookie(request, response, OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME);
			cookieUtil.deleteCookie(request, response, REDIRECT_URL_PARAM_COOKIE_NAME);
			return;
		}
		cookieUtil.setCookie(
			response,
			OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME,
			cookieUtil.serialize(authorizationRequest),
			cookieExpireSeconds);
		String redirectUrlAfterLogin = request.getParameter(REDIRECT_URL_PARAM_COOKIE_NAME);
		if (StringUtils.isNotBlank(redirectUrlAfterLogin)) {
			cookieUtil.setCookie(response, REDIRECT_URL_PARAM_COOKIE_NAME, redirectUrlAfterLogin, cookieExpireSeconds);
		}
	}

	@Override
	public OAuth2AuthorizationRequest removeAuthorizationRequest(
		HttpServletRequest request, HttpServletResponse response) {
		OAuth2AuthorizationRequest authorizationRequest = this.loadAuthorizationRequest(request);
		cookieUtil.deleteCookie(request, response, OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME);
		return authorizationRequest;
	}

	public void clearCookies(HttpServletRequest request, HttpServletResponse response) {
		cookieUtil.deleteCookie(request, response, OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME);
		cookieUtil.deleteCookie(request, response, REDIRECT_URL_PARAM_COOKIE_NAME);
	}
}
