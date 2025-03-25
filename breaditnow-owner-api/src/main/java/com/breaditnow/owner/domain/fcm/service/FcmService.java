package com.breaditnow.owner.domain.fcm.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.breaditnow.domain.domain.owner.entity.Owner;
import com.breaditnow.domain.domain.owner.repository.OwnerRepository;
import com.breaditnow.owner.domain.fcm.controller.req.FcmSendRequest;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FcmService {
	private final OwnerRepository ownerRepository;

	@Transactional
	public void updateToken(Long ownerId, FcmSendRequest fcmSendRequest) {
		Owner owner = ownerRepository.getById(ownerId);
		owner.changeFcmToken(fcmSendRequest.fcmToken());
	}
}
