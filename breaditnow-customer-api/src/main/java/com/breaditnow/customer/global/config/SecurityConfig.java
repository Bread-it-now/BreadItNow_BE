package com.breaditnow.customer.global.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.breaditnow.common.security.jwt.filter.JwtAuthenticationFilter;
import com.breaditnow.common.security.jwt.filter.JwtExceptionHandlerFilter;
import com.breaditnow.common.security.jwt.handler.JwtAccessDeniedHandler;
import com.breaditnow.common.security.jwt.handler.JwtAuthenticationEntryPoint;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	private static final String[] HEALTH_CHECK = {"/api/check"};

	private final JwtAuthenticationFilter jwtAuthenticationFilter;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
			.cors(cors -> cors.configurationSource(corsConfigurationSource()))
			.csrf(AbstractHttpConfigurer::disable)
			.formLogin(AbstractHttpConfigurer::disable)
			.httpBasic(AbstractHttpConfigurer::disable)
			.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		
		http
			.authorizeHttpRequests(authorize -> authorize
				.requestMatchers(HEALTH_CHECK).permitAll()
				.anyRequest().permitAll()
			)

			.exceptionHandling((exceptions) -> exceptions
				.authenticationEntryPoint(new JwtAuthenticationEntryPoint()) // 인증 실패 핸들링
				.accessDeniedHandler(new JwtAccessDeniedHandler()) // 인가 실패 핸들링
			)
			.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
			.addFilterBefore(new JwtExceptionHandlerFilter(), JwtAuthenticationFilter.class);

		return http.build();
	}

	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowedOrigins(List.of("https://localhost:3000"));
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
