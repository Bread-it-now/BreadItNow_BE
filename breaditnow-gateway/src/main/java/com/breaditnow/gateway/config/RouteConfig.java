package com.breaditnow.gateway.config;

// @Component
// public class RouteConfig {
// 	@Bean
// 	public RouteLocator routeLocator(RouteLocatorBuilder builder) {
// 		return builder.routes()
// 			.route("oauth_route", r -> r
// 				.path("/oauth2/authorization/**")
// 				.or()
// 				.path("/oauth/callback/**")
// 				.uri("http://localhost:9000"))
// 			.route("auth_api", r -> r
// 				.path("/auth-api/**")
// 				.filters(f -> f.rewritePath("/auth-api/(?<segment>.*)", "/${segment}"))
// 				.uri("http://localhost:9000"))
// 			.route("customer_api", r -> r
// 				.path("/customer-api/**")
// 				.filters(f -> f.rewritePath("/customer-api/(?<segment>.*)", "/${segment}"))
// 				.uri("http://localhost:9001"))
// 			.route("owner_api", r -> r
// 				.path("/owner-api/**")
// 				.filters(f -> f.rewritePath("/owner-api/(?<segment>.*)", "/${segment}"))
// 				.uri("http://localhost:9002"))
// 			.build();
// 	}
// }
