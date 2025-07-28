package com.breaditnow.token.service;

import com.breaditnow.auth.adatper.in.security.AccountContext;
import com.breaditnow.auth.adatper.out.jwt.JwtTokenCreator;
import com.breaditnow.auth.adatper.out.jwt.JwtTokenValidator;
import com.breaditnow.auth.adatper.out.jwt.dto.AuthToken;
import com.breaditnow.auth.domain.port.out.AuthTokenRepository;
import com.breaditnow.common.exception.AuthException;
import com.breaditnow.common.util.CookieUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.breaditnow.auth.adatper.out.jwt.dto.AuthTokenType.ACCESS;
import static com.breaditnow.common.exception.AuthErrorCode.*;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.security.oauth2.core.OAuth2AccessToken.TokenType.BEARER;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TokenService {
	private final JwtTokenValidator jwtTokenValidator;
	private final JwtTokenCreator jwtTokenCreator;
	private final AuthTokenRepository authTokenRepository;
	private final CookieUtil cookieUtil;

	@Value("${auth.token.refresh-cookie-key}")
	private String cookieKey;

	@Transactional
	public void reissueToken(HttpServletRequest request, HttpServletResponse response) {
		String cookieRefreshToken = cookieUtil.getCookie(request, cookieKey)
			.map(Cookie::getValue).orElseThrow(() -> new AuthException(REFRESH_TOKEN_NOT_EXIST_IN_COOKIE));

		Authentication authentication = jwtTokenValidator.getAuthentication(cookieRefreshToken);
		AccountContext accountContext = (AccountContext)authentication.getPrincipal();

		validateRefreshToken(cookieRefreshToken, accountContext);

		AuthToken accessToken = jwtTokenCreator.createToken(authentication, ACCESS);

		final String bearerPrefix = BEARER.getValue() + " ";
		response.setHeader(AUTHORIZATION, bearerPrefix + accessToken.token());
	}

	private void validateRefreshToken(String oldToken, AccountContext accountContext) {
		if (!jwtTokenValidator.validateToken(oldToken)) {
			throw new AuthException(REFRESH_TOKEN_EXPIRED);
		}

		AuthToken savedRefreshToken = authTokenRepository.findRefreshToken(accountContext.getAccount().getId())
			.orElseThrow(() -> new AuthException(REFRESH_TOKEN_EXPIRED));

		if (!oldToken.equals(savedRefreshToken.token())) {
			throw new AuthException(TOKEN_NOT_MATCHED);
		}
	}
}
