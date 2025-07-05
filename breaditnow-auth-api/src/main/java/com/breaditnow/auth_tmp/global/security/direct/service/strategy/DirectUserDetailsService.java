package com.breaditnow.auth_tmp.global.security.direct.service.strategy;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface DirectUserDetailsService extends UserDetailsService {
	String getRole();
}
