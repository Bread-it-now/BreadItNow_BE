package com.breaditnow.auth.domain.customer.service.strategy;

import com.breaditnow.auth.domain.customer.controller.req.SignupRequest;
import com.breaditnow.auth.global.security.Role;

public interface SignupStrategy {
	Long signup(SignupRequest signupRequest);

	Role getRole();
}
