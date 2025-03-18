package com.breaditnow.owner.domain.bakery.service;

import static com.breaditnow.domain.domain.bakery.enumerate.OperatingStatus.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.breaditnow.common.client.kakao.GeoLocationClient;
import com.breaditnow.common.client.kakao.dto.res.AddressCoordinate;
import com.breaditnow.domain.domain.bakery.entity.Address;
import com.breaditnow.domain.domain.bakery.entity.Bakery;
import com.breaditnow.domain.domain.bakery.entity.BakeryImage;
import com.breaditnow.domain.domain.bakery.enumerate.OperatingStatus;
import com.breaditnow.domain.domain.bakery.repository.BakeryRepository;
import com.breaditnow.domain.domain.owner.entity.Owner;
import com.breaditnow.domain.domain.owner.repository.OwnerRepository;
import com.breaditnow.domain.domain.region.entity.RegionPK;
import com.breaditnow.domain.domain.region.repository.RegionRepository;
import com.breaditnow.owner.domain.bakery.controller.req.BakeryCreateRequest;
import com.breaditnow.owner.domain.bakery.controller.req.BakeryUpdateRequest;
import com.breaditnow.owner.domain.bakery.controller.res.BakeryResponse;
import com.breaditnow.owner.global.exception.OwnerErrorCode;
import com.breaditnow.owner.global.exception.OwnerException;
import com.breaditnow.owner.global.s3.FileUploader;

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
	private final GeoLocationClient geoLocationClient;

	@Transactional
	public Long createBakery(Long ownerId, BakeryCreateRequest bakeryCreateRequest, MultipartFile profileImage) {
		Owner owner = ownerRepository.getById(ownerId);

		bakeryRepository.checkDuplicateOwner(ownerId);

		RegionPK regionPK = new RegionPK(bakeryCreateRequest.addressCode());
		regionRepository.checkExists(regionPK);

		Address address = new Address(regionPK, bakeryCreateRequest.address());
		AddressCoordinate addressCoordinate = geoLocationClient.lookupCoordinates(
			bakeryCreateRequest.address());
		if (addressCoordinate == null) {
			throw new OwnerException(OwnerErrorCode.COORDINATE_NOT_FOUND);
		}
		address.setLatitude(Double.valueOf(addressCoordinate.x()));
		address.setLongitude(Double.valueOf(addressCoordinate.y()));

		String profileImageUrl = uploadFile(profileImage, "image/owner/" + ownerId + "/bakery/profile");

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
		Bakery bakery = bakeryRepository.getByOwnerIdAndId(ownerId, bakeryId);

		RegionPK regionPK = new RegionPK(bakeryUpdateRequest.addressCode());
		regionRepository.checkExists(regionPK);

		String updatedProfileImage = uploadFile(profileImage, "image/owner/" + ownerId + "/bakery/profile");

		Address address = new Address(regionPK, bakeryUpdateRequest.address());
		AddressCoordinate addressCoordinate = geoLocationClient.lookupCoordinates(bakeryUpdateRequest.address());
		if (addressCoordinate == null) {
			throw new OwnerException(OwnerErrorCode.COORDINATE_NOT_FOUND);
		}
		address.setLatitude(Double.valueOf(addressCoordinate.x()));
		address.setLongitude(Double.valueOf(addressCoordinate.y()));

		List<BakeryImage> bakeryImages = new ArrayList<>();
		if (bakeryImageFiles != null && !bakeryImageFiles.isEmpty()) {
			bakeryImages = bakeryImageFiles.stream()
				.map(file -> uploader.upload(file, "image/owner/" + ownerId + "/bakery/gallery"))
				.map(bakeryImageUrl -> new BakeryImage(bakery, bakeryImageUrl))
				.collect(Collectors.toList());
		}

		bakery.update(Bakery.builder()
			.owner(bakery.getOwner())
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
		bakery.changeIsActive(false);
		return bakery.getId();
	}

	@Transactional
	public Long updateOperatingStatus(Long ownerId, Long bakeryId, String type) {
		Bakery bakery = bakeryRepository.getByOwnerIdAndId(ownerId, bakeryId);
		bakery.updateOperatingStatus(OperatingStatus.from(type));
		return bakery.getId();
	}

	private String uploadFile(MultipartFile file, String path) {
		if (file != null && !file.isEmpty()) {
			return uploader.upload(file, path);
		}
		return "";
	}
}
