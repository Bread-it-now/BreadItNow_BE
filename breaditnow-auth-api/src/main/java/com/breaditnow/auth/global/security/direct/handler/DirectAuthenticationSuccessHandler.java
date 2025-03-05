package com.breaditnow.auth.global.security.direct.handler;

import static com.breaditnow.auth.global.security.jwt.token.AuthTokenType.*;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.breaditnow.auth.domain.token.repository.AuthTokenRepository;
import com.breaditnow.auth.global.security.AccountContext;
import com.breaditnow.auth.global.security.jwt.provider.JwtTokenCreator;
import com.breaditnow.auth.global.security.jwt.token.AuthToken;
import com.breaditnow.common.response.ApiSuccessResponse;
import com.breaditnow.common.util.CookieUtil;
import com.breaditnow.domain.domain.customer.repository.CustomerRepository;
import com.breaditnow.domain.domain.owner.repository.OwnerRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class DirectAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
	private final JwtTokenCreator jwtTokenCreator;
	private final AuthTokenRepository authTokenRepository;
	private final CookieUtil cookieUtil;
	private final CustomerRepository customerRepository;
	private final OwnerRepository ownerRepository;

	@Value("${auth.token.refresh-cookie-key}")
	private String refreshCookieKey;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
		Authentication authentication) throws IOException {
		log.info("DirectAuthenticationSuccessHandler");

		AccountContext accountContext = (AccountContext)authentication.getPrincipal();
		Long userId = accountContext.getUserId();

		boolean isOwner = accountContext.getAuthorities().stream()
			.anyMatch(auth -> "ROLE_OWNER".equalsIgnoreCase(auth.getAuthority()));

		boolean isNewUser;
		if (isOwner) {
			isNewUser = false; // Owner는 항상 false로 관리?
		} else {
			isNewUser = (customerRepository.getById(userId).getNickname() == null);
		}

		AuthToken accessToken = jwtTokenCreator.createToken(authentication, ACCESS);
		response.addHeader("Authorization", "Bearer " + accessToken.token());

		AuthToken refreshToken = jwtTokenCreator.createToken(authentication, REFRESH);
		authTokenRepository.saveToken(refreshToken, accountContext.getRole());
		int maxAge = Math.toIntExact(refreshToken.expiresIn() / 1000);
		cookieUtil.addCookie(response, refreshCookieKey, refreshToken.token(), maxAge);

		response.setContentType("application/json;charset=UTF-8");
		String responseBody = new ObjectMapper().writeValueAsString(ApiSuccessResponse.of("isNewUser", isNewUser));
		response.getWriter().write(responseBody);
	}
}
