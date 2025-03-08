package com.breaditnow.customer.global.config;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.breaditnow.customer.domain.bakeryfavorite.controller.req.converter.BakeryFavoriteSortTypeConverter;
import com.breaditnow.customer.global.security.resolver.CustomerArgumentResolver;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {
	private final BakeryFavoriteSortTypeConverter bakeryFavoriteSortTypeConverter;
	private final CustomerArgumentResolver customerArgumentResolver;

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
		resolvers.add(customerArgumentResolver);
	}

	@Override
	public void addFormatters(FormatterRegistry registry) {
		registry.addConverter(bakeryFavoriteSortTypeConverter);
	}

}
