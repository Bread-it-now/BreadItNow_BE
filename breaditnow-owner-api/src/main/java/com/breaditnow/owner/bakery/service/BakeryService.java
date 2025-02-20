package com.breaditnow.owner.bakery.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.breaditnow.domain.bakery.repository.AddressRepository;
import com.breaditnow.domain.bakery.repository.BakeryRepository;
import com.breaditnow.domain.owner.entity.Owner;
import com.breaditnow.domain.owner.repository.OwnerRepository;
import com.breaditnow.owner.bakery.controller.req.BakeryCreateRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BakeryService {
	private OwnerRepository ownerRepository;
	private AddressRepository addressRepository;
	private BakeryRepository bakeryRepository;

	@Transactional
	public Long createBakery(Long ownerId, BakeryCreateRequest request) {
		Owner owner = ownerRepository.getById(ownerId);

		return 0L;
	}
}
