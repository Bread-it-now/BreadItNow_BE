//package com.breaditnow.token.service;
//
//import static org.springframework.http.HttpHeaders.*;
//import static org.springframework.security.oauth2.core.OAuth2AccessToken.TokenType.*;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.security.core.Authentication;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import com.breaditnow.token.repository.AuthTokenRepository;
//import com.breaditnow.common.exception.AuthException;
//import com.breaditnow.auth_tmp.global.security.AccountContext;
//import com.breaditnow.auth_tmp.global.security.jwt.provider.JwtTokenCreator;
//import com.breaditnow.auth_tmp.global.security.jwt.provider.JwtTokenValidator;
//import com.breaditnow.auth_tmp.global.security.jwt.token.AuthToken;
//import com.breaditnow.auth_tmp.global.security.jwt.token.AuthTokenType;
//import com.breaditnow.common.util.CookieUtil;
//
//import jakarta.servlet.http.Cookie;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.RequiredArgsConstructor;
//
//@Service
//@RequiredArgsConstructor
//@Transactional(readOnly = true)
//public class TokenService {
//
//	private final JwtTokenValidator jwtTokenValidator;
//	private final JwtTokenCreator jwtTokenCreator;
//	private final AuthTokenRepository authTokenRepository;
//	private final CookieUtil cookieUtil;
//
//	@Value("${auth.token.refresh-cookie-key}")
//	private String cookieKey;
//
//	@Transactional
//	public void reissueToken(HttpServletRequest request, HttpServletResponse response) {
//		String cookieRefreshToken = cookieUtil.getCookie(request, cookieKey)
//			.map(Cookie::getValue).orElseThrow(() -> new AuthException(REFRESH_TOKEN_NOT_EXIST_IN_COOKIE));
//
//		Authentication authentication = jwtTokenValidator.getAuthentication(cookieRefreshToken);
//		AccountContext accountContext = (AccountContext)authentication.getPrincipal();
//
//		validateRefreshToken(cookieRefreshToken, accountContext);
//
//		AuthToken accessToken = jwtTokenCreator.createToken(authentication, ACCESS);
//
//		final String bearerPrefix = BEARER.getValue() + " ";
//		response.setHeader(AUTHORIZATION, bearerPrefix + accessToken.token());
//	}
//
//	private void validateRefreshToken(String oldToken, AccountContext accountContext) {
//		if (!jwtTokenValidator.validateToken(oldToken)) {
//			throw new AuthException(REFRESH_TOKEN_EXPIRED);
//		}
//
//		AuthToken savedRefreshToken = authTokenRepository.findToken(AuthTokenType.REFRESH, accountContext.getRole(),
//				accountContext.getUserId())
//			.orElseThrow(() -> new AuthException(REFRESH_TOKEN_EXPIRED));
//
//		if (!oldToken.equals(savedRefreshToken.token())) {
//			throw new AuthException(TOKEN_NOT_MATCHED);
//		}
//	}
//}
