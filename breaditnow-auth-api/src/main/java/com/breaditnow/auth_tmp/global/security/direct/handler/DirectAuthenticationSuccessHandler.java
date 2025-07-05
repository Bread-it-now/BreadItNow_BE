//package com.breaditnow.auth_tmp.global.security.direct.handler;
//
//import static com.breaditnow.auth_tmp.global.security.Role.*;
//import static com.breaditnow.auth_tmp.global.security.jwt.token.AuthTokenType.*;
//import static org.springframework.http.HttpHeaders.*;
//import static org.springframework.http.MediaType.*;
//import static org.springframework.security.oauth2.core.OAuth2AccessToken.TokenType.*;
//
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.Map;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
//import org.springframework.stereotype.Component;
//
//import com.breaditnow.token.repository.AuthTokenRepository;
//import com.breaditnow.auth_tmp.global.security.AccountContext;
//import com.breaditnow.auth_tmp.global.security.jwt.provider.JwtTokenCreator;
//import com.breaditnow.auth_tmp.global.security.jwt.token.AuthToken;
//import com.breaditnow.common.response.ApiSuccessResponse;
//import com.breaditnow.common.util.CookieUtil;
//import com.breaditnow.domain.domain.bakery.entity.Bakery;
//import com.breaditnow.domain.domain.bakery.repository.BakeryRepository;
//import com.breaditnow.domain.domain.customer.entity.Customer;
//import com.breaditnow.domain.domain.customer.repository.CustomerRepository;
//import com.breaditnow.domain.domain.owner.repository.OwnerRepository;
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.RequiredArgsConstructor;
//
//@Component
//@RequiredArgsConstructor
//public class DirectAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
//	private final JwtTokenCreator jwtTokenCreator;
//	private final AuthTokenRepository authTokenRepository;
//	private final CookieUtil cookieUtil;
//	private final CustomerRepository customerRepository;
//	private final BakeryRepository bakeryRepository;
//	private final OwnerRepository ownerRepository;
//
//	@Value("${auth.token.refresh-cookie-key}")
//	private String refreshCookieKey;
//
//	@Override
//	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
//		Authentication authentication) throws IOException {
//
//		AccountContext accountContext = (AccountContext)authentication.getPrincipal();
//		Long userId = accountContext.getUserId();
//
//		boolean isOwner = accountContext.getAuthorities().stream()
//			.anyMatch(auth -> toAuthority(OWNER).equalsIgnoreCase(auth.getAuthority()));
//
//		boolean isNewUser;
//		String fcmToken;
//		Long bakeryId = null;
//		if (isOwner) {
//			isNewUser = !bakeryRepository.existsByOwnerIdAndIsActiveTrue(userId);
//			fcmToken = ownerRepository.getById(userId).getFcmToken();
//			bakeryId = bakeryRepository.findByOwnerIdAndIsActiveTrue(userId)
//				.map(Bakery::getId)
//				.orElse(null);
//		} else {
//			Customer customer = customerRepository.getById(userId);
//			isNewUser = customer.getNickname() == null;
//			fcmToken = customer.getFcmToken();
//		}
//
//		boolean hasFcmToken = fcmToken != null && !fcmToken.trim().isEmpty();
//
//		AuthToken accessToken = jwtTokenCreator.createToken(authentication, ACCESS);
//
//		final String bearerPrefix = BEARER.getValue() + " ";
//		response.addHeader(AUTHORIZATION, bearerPrefix + accessToken.token());
//
//		AuthToken refreshToken = jwtTokenCreator.createToken(authentication, REFRESH);
//		authTokenRepository.saveToken(refreshToken, accountContext.getRole());
//		int maxAge = Math.toIntExact(refreshToken.expiresIn() / 1000);
//		cookieUtil.addCookie(response, refreshCookieKey, refreshToken.token(), maxAge);
//
//		response.setContentType(APPLICATION_JSON_VALUE);
//
//		Map<String, Object> responseData = new HashMap<>();
//		responseData.put("isNewUser", isNewUser);
//		responseData.put("hasFcmToken", hasFcmToken);
//		if (bakeryId != null) {
//			responseData.put("bakeryId", bakeryId);
//		}
//
//		String responseBody = new ObjectMapper().writeValueAsString(ApiSuccessResponse.of(responseData));
//		response.getWriter().write(responseBody);
//	}
//}
