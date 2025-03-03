package com.breaditnow.auth.domain.auth.service.strategy;

import static com.breaditnow.auth.global.exception.AuthErrorCode.*;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.breaditnow.auth.domain.auth.controller.req.SignupRequest;
import com.breaditnow.auth.global.exception.AuthException;
import com.breaditnow.auth.global.security.Role;
import com.breaditnow.domain.domain.owner.entity.Owner;
import com.breaditnow.domain.domain.owner.repository.OwnerRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OwnerSignupStrategy implements SignupStrategy {
	private final OwnerRepository ownerRepository;
	private final PasswordEncoder passwordEncoder;

	@Override
	public Long signup(SignupRequest signupRequest) {
		Optional<Owner> existing = ownerRepository.findByEmail(signupRequest.email());
		if (existing.isPresent()) {
			throw new AuthException(EMAIL_ALREADY_EXISTS);
		}

		Owner owner = Owner.builder()
			.email(signupRequest.email())
			.password(passwordEncoder.encode(signupRequest.password()))
			.build();
		return ownerRepository.save(owner).getId();
	}

	@Override
	public Role getRole() {
		return Role.OWNER;
	}
}
