package com.breaditnow.auth.global.config;

import static org.springframework.http.HttpHeaders.*;
import static org.springframework.http.HttpMethod.*;

import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.breaditnow.auth.global.security.direct.filter.DirectLoginFilter;
import com.breaditnow.auth.global.security.direct.handler.DirectAuthenticationFailureHandler;
import com.breaditnow.auth.global.security.direct.handler.DirectAuthenticationSuccessHandler;
import com.breaditnow.auth.global.security.direct.provider.DirectAuthenticationProvider;
import com.breaditnow.auth.global.security.direct.service.strategy.CustomCustomerDetailsService;
import com.breaditnow.auth.global.security.direct.service.strategy.CustomOwnerDetailsService;
import com.breaditnow.auth.global.security.direct.service.strategy.DirectUserDetailsService;
import com.breaditnow.auth.global.security.oauth2.cookie.CookieOAuth2AuthorizationRequestRepository;
import com.breaditnow.auth.global.security.oauth2.handler.Oauth2AuthenticationFailureHandler;
import com.breaditnow.auth.global.security.oauth2.handler.Oauth2AuthenticationSuccessHandler;
import com.breaditnow.auth.global.security.oauth2.service.CustomOAuth2UserService;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	private static final String[] HEALTH_CHECK = {"/api/check"};
	private static final String[] AUTH = {"/oauth2/authorization/**", "/oauth/callback/**", "/api/v1/token/**",
		"/api/v1/auth/**"};

	private final CustomOAuth2UserService oauth2UserService;
	private final CookieOAuth2AuthorizationRequestRepository cookieOAuth2AuthorizationRequestRepository;
	private final Oauth2AuthenticationSuccessHandler oauth2AuthenticationSuccessHandler;
	private final Oauth2AuthenticationFailureHandler oauth2AuthenticationFailureHandler;

	private final DirectAuthenticationSuccessHandler directAuthenticationSuccessHandler;
	private final DirectAuthenticationFailureHandler directAuthenticationFailureHandler;

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
					.authorizationRequestRepository(cookieOAuth2AuthorizationRequestRepository))
				.redirectionEndpoint(redirection -> redirection
					.baseUri("/oauth/callback/*"))
				.userInfoEndpoint(userInfo -> userInfo
					.userService(oauth2UserService))
				.successHandler(oauth2AuthenticationSuccessHandler)
				.failureHandler(oauth2AuthenticationFailureHandler));

		http
			.authorizeHttpRequests(authorize -> authorize
				.requestMatchers(HEALTH_CHECK).permitAll()
				.requestMatchers(AUTH).permitAll()
				.anyRequest().permitAll());

		return http.build();
	}

	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowedOrigins(
			List.of("http://localhost:3000", "https://localhost:3000",
				"http://www.breaditnow.com", "https://www.breaditnow.com"));
		config.setAllowedMethods(
			List.of(GET.name(), POST.name(), DELETE.name(), PUT.name(), PATCH.name(), OPTIONS.name()));
		config.setAllowedHeaders(List.of(AUTHORIZATION, SET_COOKIE, CONTENT_TYPE));
		config.setExposedHeaders(List.of(AUTHORIZATION, SET_COOKIE));
		config.setAllowCredentials(true);
		config.setMaxAge(3600L);

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", config);
		return source;
	}

	@Bean
	public DirectLoginFilter directLoginFilter(DirectAuthenticationProvider directAuthenticationProvider) {
		ProviderManager providerManager = new ProviderManager(List.of(directAuthenticationProvider));

		DirectLoginFilter directLoginFilter = new DirectLoginFilter();
		directLoginFilter.setAuthenticationManager(providerManager);
		directLoginFilter.setAuthenticationSuccessHandler(directAuthenticationSuccessHandler);
		directLoginFilter.setAuthenticationFailureHandler(directAuthenticationFailureHandler);
		directLoginFilter.setFilterProcessesUrl("/api/v1/auth/sign-in");
		directLoginFilter.setPostOnly(true);
		return directLoginFilter;
	}

	@Bean
	public DirectAuthenticationProvider directAuthenticationProvider(
		PasswordEncoder passwordEncoder,
		CustomCustomerDetailsService customerDetailsService,
		CustomOwnerDetailsService ownerDetailsService) {

		Map<String, DirectUserDetailsService> serviceMap = Map.of(
			customerDetailsService.getRole().toUpperCase(), customerDetailsService,
			ownerDetailsService.getRole().toUpperCase(), ownerDetailsService
		);

		return new DirectAuthenticationProvider(passwordEncoder, serviceMap);
	}

	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
		return (web) -> web.ignoring().requestMatchers("api/v1/auth/sign-up");
	}
}
