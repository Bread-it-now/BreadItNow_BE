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
import com.breaditnow.common.util.CookieUtil;
import com.breaditnow.domain.domain.customer.entity.Customer;
import com.breaditnow.domain.domain.customer.repository.CustomerRepository;

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

	@Value("${auth.token.refresh-cookie-key}")
	private String refreshCookieKey;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
		Authentication authentication) throws IOException {
		log.info("DirectAuthenticationSuccessHandler");

		AccountContext accountContext = (AccountContext)authentication.getPrincipal();
		Long userId = accountContext.getUserId();

		Customer customer = customerRepository.getById(userId);
		boolean isNewUser = (customer.getNickname() == null);

		AuthToken accessToken = jwtTokenCreator.createToken(authentication, ACCESS);
		response.addHeader("Authorization", "Bearer " + accessToken.token());

		AuthToken refreshToken = jwtTokenCreator.createToken(authentication, REFRESH);
		authTokenRepository.saveToken(refreshToken);
		int maxAge = Math.toIntExact(refreshToken.expiresIn() / 1000);
		cookieUtil.addCookie(response, refreshCookieKey, refreshToken.token(), maxAge);

		response.setContentType("application/json;charset=UTF-8");
		String responseBody = String.format("""
			{
				"status": "SUCCESS",
				"data": {
					"isNewUser": %s
				}
			}
			""", isNewUser);
		response.getWriter().write(responseBody);
	}
}
