package com.breaditnow.gateway.filter;

import java.util.List;
import java.util.function.Predicate;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

@Component
public class RouteValidator {

	public static final List<String> openApiEndpoints = List.of(
		"/customer-api/api/check", "/owner-api/api/check"
	);

	public Predicate<ServerHttpRequest> isPassed =
		request -> openApiEndpoints
			.stream()
			.anyMatch(uri -> request.getURI().getPath().contains(uri));
}
