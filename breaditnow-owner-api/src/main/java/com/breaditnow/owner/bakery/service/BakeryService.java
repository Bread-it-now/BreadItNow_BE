package com.breaditnow.owner.bakery.service;

import static com.breaditnow.domain.bakery.enumerate.OperatingStatus.*;

import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.breaditnow.domain.bakery.entity.Address;
import com.breaditnow.domain.bakery.entity.Bakery;
import com.breaditnow.domain.bakery.repository.BakeryRepository;
import com.breaditnow.domain.owner.entity.Owner;
import com.breaditnow.domain.owner.repository.OwnerRepository;
import com.breaditnow.domain.region.entity.Region;
import com.breaditnow.domain.region.entity.RegionPK;
import com.breaditnow.domain.region.repository.RegionRepository;
import com.breaditnow.owner.bakery.controller.req.BakeryCreateRequest;
import com.breaditnow.owner.bakery.controller.req.BakeryUpdateRequest;
import com.breaditnow.owner.bakery.controller.res.BakeryResponse;
import com.breaditnow.owner.global.s3.S3Uploader;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BakeryService {
	private final RegionRepository regionRepository;
	private final OwnerRepository ownerRepository;
	private final BakeryRepository bakeryRepository;
	private final S3Uploader uploader;

	@Transactional
	public Long createBakery(Long ownerId, BakeryCreateRequest bakeryCreateRequest, MultipartFile multipartFile) throws
		IOException {
		Owner owner = ownerRepository.getById(ownerId);

		RegionPK regionPK = new RegionPK(bakeryCreateRequest.addressCode());
		Region region = regionRepository.getById(regionPK);

		Address address = Address.builder()
			.description(bakeryCreateRequest.addressDescription())
			.region(region) // latitude, longitude 가져오기
			.build();

		String profileImageUrl = uploader.upload(multipartFile, "/image/profile");

		Bakery bakery = Bakery.builder()
			.owner(owner)
			.name(bakeryCreateRequest.name())
			.phone(bakeryCreateRequest.phone())
			.introduction(bakeryCreateRequest.introduction())
			.profileImage(profileImageUrl)
			.openTime(bakeryCreateRequest.openTime())
			.address(address)
			.operatingStatus(CLOSED)
			.build();
		
		Bakery savedBakery = bakeryRepository.save(bakery);
		return savedBakery.getId();
	}

	public BakeryResponse getBakery(Long bakeryId) {
		Bakery bakery = bakeryRepository.getById(bakeryId);
		return BakeryResponse.of(bakery);
	}

	@Transactional
	public BakeryResponse updateBakery(Long ownerId, Long bakeryId, BakeryUpdateRequest bakeryUpdateRequest) {
		Owner owner = ownerRepository.getById(ownerId);

		Bakery bakery = bakeryRepository.getByOwnerIdAndBakeryId(ownerId, bakeryId);

		RegionPK regionPK = new RegionPK(bakeryUpdateRequest.addressCode());
		Region region = regionRepository.getById(regionPK);

		Bakery newBakery = Bakery.builder()
			.owner(owner)
			.name(bakeryUpdateRequest.name())
			.phone(bakeryUpdateRequest.phone())
			.introduction(bakeryUpdateRequest.introduction())
			.profileImage(bakeryUpdateRequest.profileImage())
			.openTime(bakeryUpdateRequest.openTime())
			.address(bakery.getAddress())
			.bakeryImage(bakeryUpdateRequest.additionalImages())
			.operatingStatus(CLOSED)
			.build();

		bakery.update(newBakery);
		return BakeryResponse.of(bakery);
	}
}
