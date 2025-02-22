package com.breaditnow.auth.global.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.breaditnow.auth.global.security.oauth2.cookie.CookieOAuth2AuthorizationRequestRepository;
import com.breaditnow.auth.global.security.oauth2.handler.Oauth2AuthenticationFailureHandler;
import com.breaditnow.auth.global.security.oauth2.handler.Oauth2AuthenticationSuccessHandler;
import com.breaditnow.auth.global.security.oauth2.service.CustomOAuth2UserService;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	private static final String[] AUTH = {"/oauth2/**", "/oauth/callback/**"};

	private final CustomOAuth2UserService oauth2UserService;
	private final CookieOAuth2AuthorizationRequestRepository
		cookieOAuth2AuthorizationRequestRepository;
	private final Oauth2AuthenticationSuccessHandler oauth2AuthenticationSuccessHandler;
	private final Oauth2AuthenticationFailureHandler oauth2AuthenticationFailureHandler;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
			.cors(cors -> cors.configurationSource(corsConfigurationSource()))
			.csrf(AbstractHttpConfigurer::disable)
			.formLogin(AbstractHttpConfigurer::disable)
			.httpBasic(AbstractHttpConfigurer::disable)
			.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.oauth2Login(oauth2 -> oauth2
				.authorizationEndpoint(authorization -> authorization
					.baseUri("/oauth2/authorization")
					.authorizationRequestRepository(cookieOAuth2AuthorizationRequestRepository)
				)
				.redirectionEndpoint(redirection -> redirection
					.baseUri("/oauth/callback/*"))
				.userInfoEndpoint(userInfo -> userInfo
					.userService(oauth2UserService))
				.successHandler(oauth2AuthenticationSuccessHandler)
				.failureHandler(oauth2AuthenticationFailureHandler)
			);

		http.authorizeHttpRequests(authorize -> authorize
			.requestMatchers(AUTH).permitAll()
			.anyRequest().authenticated()
		);

		return http.build();
	}

	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowedOrigins(List.of("https://localhost:9000"));
		config.setAllowedMethods(List.of("GET", "POST", "DELETE", "PUT", "PATCH", "OPTIONS"));
		config.setAllowedHeaders(List.of("Authorization", "Set-Cookie", "Content-Type"));
		config.setExposedHeaders(List.of("Authorization", "Set-Cookie"));
		config.setAllowCredentials(true);
		config.setMaxAge(3600L);

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", config);
		return source;
	}
}
