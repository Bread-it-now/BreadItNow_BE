package com.breaditnow.owner.bakery.service;

import static com.breaditnow.domain.domain.bakery.enumerate.OperatingStatus.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.breaditnow.domain.domain.bakery.entity.Address;
import com.breaditnow.domain.domain.bakery.entity.Bakery;
import com.breaditnow.domain.domain.bakery.entity.BakeryImage;
import com.breaditnow.domain.domain.bakery.enumerate.OperatingStatus;
import com.breaditnow.domain.domain.bakery.repository.BakeryRepository;
import com.breaditnow.domain.domain.owner.entity.Owner;
import com.breaditnow.domain.domain.owner.repository.OwnerRepository;
import com.breaditnow.domain.domain.region.entity.Region;
import com.breaditnow.domain.domain.region.entity.RegionPK;
import com.breaditnow.domain.domain.region.repository.RegionRepository;
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
	public Long createBakery(Long ownerId, BakeryCreateRequest bakeryCreateRequest, MultipartFile profileImage) {
		Owner owner = ownerRepository.getById(ownerId);

		bakeryRepository.checkDuplicateOwner(ownerId);

		RegionPK regionPK = new RegionPK(bakeryCreateRequest.addressCode());
		Region region = regionRepository.getById(regionPK);

		Address address = Address.builder()
			.description(bakeryCreateRequest.addressDescription())
			.region(region) // latitude, longitude 가져오기
			.build();

		String profileImageUrl = "";
		if (profileImage != null) {
			profileImageUrl = uploader.upload(profileImage, "image/owner/bakery/profile");
		}

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
	public BakeryResponse updateBakery(Long ownerId, Long bakeryId, BakeryUpdateRequest bakeryUpdateRequest,
		MultipartFile profileImage, List<MultipartFile> bakeryImageFiles) {
		Bakery bakery = bakeryRepository.getById(bakeryId);
		Owner owner = ownerRepository.getById(ownerId);
		Region region = regionRepository.getById(new RegionPK(bakeryUpdateRequest.addressCode()));
		Address address = bakery.getAddress();
		address.update(region, bakeryUpdateRequest.addressDescription());

		if (!bakery.getProfileImage().isEmpty()) {
			uploader.deleteFile(bakery.getProfileImage());
		}

		String profileImageUrl = "";
		if (profileImage != null) {
			uploader.upload(profileImage, "image/owner/bakery/profile");
		}

		List<BakeryImage> bakeryImages = new ArrayList<>();
		if (!bakery.getBakeryImages().isEmpty()) {
			bakeryImages = bakeryImageFiles.stream()
				.map(file -> uploader.upload(file, "image/owner/bakery/gallery"))
				.map(bakeryImageUrl -> new BakeryImage(bakery, bakeryImageUrl))
				.collect(Collectors.toList());
		}

		bakery.update(Bakery.builder()
			.owner(owner)
			.name(bakeryUpdateRequest.name())
			.phone(bakeryUpdateRequest.phone())
			.introduction(bakeryUpdateRequest.introduction())
			.profileImage(profileImageUrl)
			.openTime(bakeryUpdateRequest.openTime())
			.address(address)
			.bakeryImages(bakeryImages)
			.operatingStatus(CLOSED)
			.build());

		return BakeryResponse.of(bakery);
	}

	@Transactional
	public Long deleteBakery(Long ownerId, Long bakeryId) {
		Bakery bakery = bakeryRepository.getByOwnerIdAndId(ownerId, bakeryId);
		bakery.updateActive(false);
		return bakery.getId();
	}

	@Transactional
	public Long updateOperatingStatus(Long ownerId, Long bakeryId, String type) {
		Bakery bakery = bakeryRepository.getByOwnerIdAndId(ownerId, bakeryId);
		bakery.updateOperatingStatus(OperatingStatus.from(type));
		return bakery.getId();
	}
}
