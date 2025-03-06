package com.breaditnow.auth.domain.auth.service;

import static com.breaditnow.auth.global.security.Role.*;
import static java.util.stream.Collectors.*;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.breaditnow.auth.domain.auth.controller.req.SignupRequest;
import com.breaditnow.auth.domain.auth.service.strategy.SignupStrategy;
import com.breaditnow.auth.global.security.Role;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthSignInService {
	private final Map<Role, SignupStrategy> signupStrategies;

	@Autowired
	public AuthSignInService(List<SignupStrategy> strategies) {
		this.signupStrategies = strategies.stream()
			.collect(toMap(SignupStrategy::getRole, strategy -> strategy));
	}

	@Transactional
	public Long signup(SignupRequest signupRequest) {
		Role role = from(signupRequest.role());
		SignupStrategy strategy = signupStrategies.get(role);
		return strategy.signup(signupRequest);
	}
}
