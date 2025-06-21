package com.breaditnow.owner.domain.owner.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.breaditnow.domain.domain.owner.entity.Owner;
import com.breaditnow.domain.domain.owner.repository.OwnerRepository;
import com.breaditnow.owner.domain.owner.controller.req.OwnerPasswordUpdateRequest;
import com.breaditnow.owner.common.exception.OwnerException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OwnerService {

	private final OwnerRepository ownerRepository;
	private final PasswordEncoder passwordEncoder;

	@Transactional
	public void updateOwnerPassword(Long ownerId, OwnerPasswordUpdateRequest ownerPasswordUpdateRequest) {
		Owner owner = ownerRepository.getById(ownerId);
		String newPassword = ownerPasswordUpdateRequest.newPassword();

		if (passwordEncoder.matches(newPassword, owner.getPassword())) {
			throw new OwnerException(PASSWORD_SAME_AS_CURRENT);
		}

		String encodedPassword = passwordEncoder.encode(newPassword);
		owner.changePassword(encodedPassword);
	}
}
