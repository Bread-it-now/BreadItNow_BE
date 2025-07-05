package com.breaditnow.config;

import com.breaditnow.auth.adatper.in.security.filter.DirectLoginFilter;
import com.breaditnow.auth.adatper.in.security.handler.DirectAuthenticationFailureHandler;
import com.breaditnow.auth.adatper.in.security.handler.DirectAuthenticationSuccessHandler;
import com.breaditnow.auth.adatper.in.security.provider.DirectAuthenticationProvider;
import com.breaditnow.auth.adatper.in.security.service.PrincipalDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private static final String[] HEALTH_CHECK = {"/api/check"};
    private static final String[] AUTH_WHITELIST = {
            "/api/check", "/api/v1/auth/**"
    };

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, DirectLoginFilter directLoginFilter) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(AUTH_WHITELIST).permitAll()
                        .anyRequest().authenticated()
                );

        http.addFilterAt(directLoginFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public DirectLoginFilter directLoginFilter(AuthenticationManager authenticationManager, DirectAuthenticationSuccessHandler successHandler, DirectAuthenticationFailureHandler failureHandler) {
        DirectLoginFilter filter = new DirectLoginFilter();
        filter.setAuthenticationManager(authenticationManager);
        filter.setAuthenticationSuccessHandler(successHandler);
        filter.setAuthenticationFailureHandler(failureHandler);
        filter.setFilterProcessesUrl("/api/v1/auth/sign-in");
        return filter;
    }

    @Bean
    public AuthenticationManager authenticationManager(DirectAuthenticationProvider directAuthenticationProvider) {
        return new ProviderManager(List.of(directAuthenticationProvider));
    }

    @Bean
    public DirectAuthenticationProvider directAuthenticationProvider(PrincipalDetailsService principalDetailsService, PasswordEncoder passwordEncoder) {
        return new DirectAuthenticationProvider(principalDetailsService, passwordEncoder);
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("http://localhost:3000")); // 프론트엔드 주소
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));

        config.setExposedHeaders(List.of("Authorization"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}
