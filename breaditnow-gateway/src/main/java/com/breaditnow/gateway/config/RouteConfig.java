package com.breaditnow.gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class RouteConfig {
	@Bean
	public RouteLocator routeLocator(RouteLocatorBuilder builder) {
		return builder.routes().route("auth_api", r -> r
				.path("/auth-api/**")
				.or()
				.path("/oauth2/authorization/**")
				.or()
				.path("/oauth/callback/**")
				.filters(f -> f.rewritePath("/auth-api/(?<segment>.*)", "/${segment}"))
				.uri("http://localhost:9000"))
			.build();
	}
}
