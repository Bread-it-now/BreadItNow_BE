package com.breaditnow.common.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.breaditnow.common.logback.mdc.MDCFilter;

@Configuration
public class FilterConfig {

	@Bean
	public FilterRegistrationBean<MDCFilter> mdcFilterRegistration(MDCFilter mdcFilter) {
		FilterRegistrationBean<MDCFilter> registrationBean = new FilterRegistrationBean<>();
		registrationBean.setFilter(mdcFilter);
		registrationBean.addUrlPatterns("/*"); // 모든 요청에 적용
		registrationBean.setOrder(1); // 필터 체인에서의 실행 순서
		return registrationBean;
	}
}
