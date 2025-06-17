package com.breaditnow.owner.bakery.application.service;

import com.breaditnow.domain.global.exception.DomainException;
import com.breaditnow.owner.bakery.application.port.in.*;
import com.breaditnow.owner.bakery.application.port.out.AddressPort;
import com.breaditnow.owner.bakery.application.port.out.BakeryRepository;
import com.breaditnow.owner.bakery.application.port.out.ImagePort;
import com.breaditnow.owner.bakery.domain.*;
import com.breaditnow.owner.bakery.infrastructure.external.api.AddressInfo;
import com.breaditnow.owner.bakery.infrastructure.presentation.request.BakeryCreateRequest;
import com.breaditnow.owner.bakery.infrastructure.presentation.request.BakeryUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

import static com.breaditnow.domain.global.exception.DomainErrorCode.BAKERY_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class BakeryManagementService implements CreateBakeryUseCase, DeleteBakeryUseCase, UpdateBakeryUseCase, UpdateOperatingStatusUseCase, UpdateProfileImageUseCase, AddBakeryImagesUseCase {
    private final ImagePort imagePort;
    private final BakeryRepository bakeryRepository;
    private final AddressPort addressPort;

    @Override
    @Transactional
    public Long createBakery(Long ownerId, BakeryCreateRequest request, MultipartFile profileImage) {
        AddressInfo addressInfo = addressPort.getAddressInfo(request.address());
        Address address = Address.create(addressInfo.regionCode(), request.address(), addressInfo.latitude(), addressInfo.longitude());
        Image image = imagePort.saveImage(profileImage);
        PhoneNumber phoneNumber = PhoneNumber.create(request.phoneNumber());
        Bakery bakery = Bakery.create(ownerId, request.name(), address, phoneNumber, image, request.openTime(), request.introduction());
        return bakeryRepository.save(bakery);
    }

    @Override
    @Transactional
    public void updateBakery(Long ownerId, Long bakeryId, BakeryUpdateRequest request) {
        Bakery bakery = findBakeryById(bakeryId);
        bakery.update(ownerId, request.name(), request.openTime(), request.introduction());
        bakeryRepository.save(bakery);
    }

    @Override
    @Transactional
    public void updateOperatingStatus(Long ownerId, Long bakeryId, OperatingStatus newStatus) {
        Bakery bakery = findBakeryById(bakeryId);
        bakery.updateOperatingStatus(ownerId, newStatus);
        bakeryRepository.save(bakery);
    }

    @Override
    @Transactional
    public void updateProfileImage(Long ownerId, Long bakeryId, MultipartFile newProfileImage) {
        Bakery bakery = findBakeryById(bakeryId);
        Image newImage = imagePort.saveImage(newProfileImage);
        bakery.updateProfileImage(ownerId, newImage);
        bakeryRepository.save(bakery);
    }

    @Override
    @Transactional
    public void addAdditionalImages(Long ownerId, Long bakeryId, List<MultipartFile> images) {
        Bakery bakery = findBakeryById(bakeryId);
        List<Image> newImages = images.stream()
                .map(imagePort::saveImage)
                .collect(Collectors.toList());
        bakery.addAdditionalImages(ownerId, newImages);
        bakeryRepository.save(bakery);
    }

    @Override
    @Transactional
    public void deleteBakery(Long ownerId, Long bakeryId) {
        Bakery bakery = findBakeryById(bakeryId);
        bakery.delete(ownerId);
        bakeryRepository.save(bakery);
    }

    public Bakery findBakeryById(Long bakeryId) {
        return bakeryRepository.findById(bakeryId)
                .orElseThrow(() -> new DomainException(BAKERY_NOT_FOUND));
    }
}