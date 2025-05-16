package com.breaditnow.gateway.filter.authentication;

import java.util.List;
import java.util.function.Predicate;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

@Component
public class RouteValidator {

	public static final List<String> openApiEndpoints = List.of(
		// Swagger
		"/v3/api-docs", "/swagger-ui",

		// health check
		"/actuator/health", "/auth-api/api/check", "/customer-api/api/check", "/owner-api/api/check",

		"/owner-api/api/v1/bread-category/search",

		"/customer-api/api/v1/bakery/hot", "/customer-api/api/v1/product/hot"
	);

	public Predicate<ServerHttpRequest> isPassed =
		request -> openApiEndpoints
			.stream()
			.anyMatch(uri -> request.getURI().getPath().contains(uri));
}
