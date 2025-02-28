package com.breaditnow.gateway.filter;

import static com.breaditnow.gateway.exception.GatewayErrorCode.*;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import com.breaditnow.gateway.exception.GatewayException;
import com.breaditnow.gateway.jwt.JwtTokenValidator;
import com.breaditnow.gateway.jwt.TokenUser;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtAuthenticationGatewayFilterFactory
	extends AbstractGatewayFilterFactory<JwtAuthenticationGatewayFilterFactory.Config> {

	private static final String BEARER_PREFIX = "Bearer ";

	@Autowired
	private RouteValidator validator;

	@Autowired
	private JwtTokenValidator jwtTokenValidator;

	public JwtAuthenticationGatewayFilterFactory() {
		super(Config.class);
	}

	@Override
	public List<String> shortcutFieldOrder() {
		return List.of("role");
	}

	@Override
	public GatewayFilter apply(Config config) {
		return (exchange, chain) -> {
			ServerHttpRequest request = exchange.getRequest();

			if (validator.isPassed.test(request)) {
				return chain.filter(exchange);
			}

			String authHeader = extractAuthorizationHeader(request);
			if (!authHeader.startsWith(BEARER_PREFIX)) {
				throw new GatewayException(INVALID_AUTHORIZATION_HEADER_FORMAT);
			}

			String token = authHeader.substring(7);
			jwtTokenValidator.validateToken(token);

			TokenUser tokenUser = jwtTokenValidator.getTokenUser(token);
			if (!hasRole(tokenUser, config.role)) {
				throw new GatewayException(UNAUTHORIZED_ACCESS);
			}

			addAuthorizationHeaders(request, tokenUser);

			return chain.filter(exchange);
		};
	}

	private static String extractAuthorizationHeader(ServerHttpRequest request) {
		List<String> authHeaders = request.getHeaders().get(HttpHeaders.AUTHORIZATION);
		if (authHeaders == null || authHeaders.isEmpty()) {
			throw new GatewayException(MISSING_AUTHORIZATION_HEADER);
		}
		return authHeaders.get(0);
	}

	private boolean hasRole(TokenUser tokenUser, String role) {
		return tokenUser.role().contains(role);
	}

	private void addAuthorizationHeaders(ServerHttpRequest request, TokenUser tokenUser) {
		request.mutate()
			.header("X-Authorization-Id", tokenUser.userId())
			.header("X-Authorization-Role", tokenUser.role())
			.build();
	}

	@Setter
	public static class Config {
		private String role;
	}
}
