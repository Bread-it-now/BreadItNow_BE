package com.breaditnow.auth.global.security.direct.service.strategy;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface DirectUserDetailsService extends UserDetailsService {
	String getRole();
}
