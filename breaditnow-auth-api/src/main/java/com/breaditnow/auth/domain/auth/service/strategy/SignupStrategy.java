package com.breaditnow.auth.domain.auth.service.strategy;

import com.breaditnow.auth.domain.auth.controller.req.SignupRequest;
import com.breaditnow.auth.global.security.Role;

public interface SignupStrategy {
	Long signup(SignupRequest signupRequest);

	Role getRole();
}
