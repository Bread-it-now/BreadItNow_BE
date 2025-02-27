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
import com.breaditnow.domain.domain.region.entity.RegionPK;
import com.breaditnow.domain.domain.region.repository.RegionRepository;
import com.breaditnow.owner.bakery.controller.req.BakeryCreateRequest;
import com.breaditnow.owner.bakery.controller.req.BakeryUpdateRequest;
import com.breaditnow.owner.bakery.controller.res.BakeryResponse;
import com.breaditnow.owner.global.s3.upload.FileUploader;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class BakeryService {
	private final RegionRepository regionRepository;
	private final OwnerRepository ownerRepository;
	private final BakeryRepository bakeryRepository;
	private final FileUploader uploader;

	@Transactional
	public Long createBakery(Long ownerId, BakeryCreateRequest bakeryCreateRequest, MultipartFile profileImage) {
		Owner owner = ownerRepository.getById(ownerId);
		bakeryRepository.checkDuplicateOwner(ownerId);

		RegionPK regionPK = new RegionPK(bakeryCreateRequest.addressCode());
		regionRepository.checkExists(regionPK);

		Address address = buildAddress(regionPK, bakeryCreateRequest.addressDescription());
		String profileImageUrl = uploadFile(profileImage, "image/owner/bakery/profile");

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
		Bakery bakery = bakeryRepository.getByIdAndIsActiveTrue(bakeryId);
		return BakeryResponse.of(bakery);
	}

	@Transactional
	public BakeryResponse updateBakery(Long ownerId, Long bakeryId, BakeryUpdateRequest bakeryUpdateRequest,
		MultipartFile profileImage, List<MultipartFile> bakeryImageFiles) {
		log.info("bakeryImageFiles = {}", bakeryImageFiles);
		Bakery bakery = bakeryRepository.getByIdAndIsActiveTrue(bakeryId);
		Owner owner = ownerRepository.getById(ownerId);

		RegionPK regionPK = new RegionPK(bakeryUpdateRequest.addressCode());
		regionRepository.checkExists(regionPK);

		Address address = Address.builder()
			.sidoCode(regionPK.getSidoCode())
			.gugunCode(regionPK.getGugunCode())
			.dongCode(regionPK.getDongCode())
			.description(bakeryUpdateRequest.addressDescription())
			.build();

		String updatedProfileImage = uploadFile(profileImage, "image/owner/bakery/profile");

		List<BakeryImage> bakeryImages = new ArrayList<>();
		if (bakeryImageFiles != null && !bakeryImageFiles.isEmpty()) {
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
			.profileImage(updatedProfileImage)
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

	private Address buildAddress(RegionPK regionPK, String description) {
		return Address.builder()
			.sidoCode(regionPK.getSidoCode())
			.gugunCode(regionPK.getGugunCode())
			.dongCode(regionPK.getDongCode())
			.description(description)
			.build();
	}

	private String uploadFile(MultipartFile file, String path) {
		if (file != null && !file.isEmpty()) {
			return uploader.upload(file, path);
		}
		return "";
	}
}
