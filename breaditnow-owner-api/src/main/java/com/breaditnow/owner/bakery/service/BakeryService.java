package com.breaditnow.owner.bakery.service;

import static com.breaditnow.domain.bakery.enumerate.OperatingStatus.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.breaditnow.domain.bakery.entity.Address;
import com.breaditnow.domain.bakery.entity.Bakery;
import com.breaditnow.domain.bakery.repository.BakeryRepository;
import com.breaditnow.domain.owner.entity.Owner;
import com.breaditnow.domain.owner.repository.OwnerRepository;
import com.breaditnow.domain.region.entity.Region;
import com.breaditnow.domain.region.entity.RegionPK;
import com.breaditnow.domain.region.repository.RegionRepository;
import com.breaditnow.owner.bakery.controller.req.BakeryCreateRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BakeryService {
	private final RegionRepository regionRepository;
	private final OwnerRepository ownerRepository;
	private final BakeryRepository bakeryRepository;

	@Transactional
	public Long createBakery(Long ownerId, BakeryCreateRequest request) {
		Owner owner = ownerRepository.getById(ownerId);
		// RegionPK 조회
		RegionPK regionPK = new RegionPK(request.addressCode());
		Region region = regionRepository.getById(regionPK);

		// Address 생성
		Address address = Address.builder()
			.description(request.addressDescription())
			.region(region) // latitude, longitude 가져오기
			.build();

		// Bakery 생성
		Bakery bakery = Bakery.builder()
			.owner(owner)
			.name(request.name())
			.phone(request.phone())
			.introduction(request.introduction())
			.profileImage(request.profileImage())
			.openTime(request.openTime())
			.address(address)
			.operatingStatus(CLOSED)
			.build();

		Bakery savedBakery = bakeryRepository.save(bakery);
		return savedBakery.getId();
	}

	public Bakery findById()
}
