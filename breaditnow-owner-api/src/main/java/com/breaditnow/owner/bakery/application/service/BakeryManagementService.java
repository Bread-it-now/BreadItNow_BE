package com.breaditnow.owner.bakery.application.service;

import com.breaditnow.domain.global.exception.DomainException;
import com.breaditnow.owner.bakery.application.port.in.CreateBakeryUseCase;
import com.breaditnow.owner.bakery.application.port.in.DeleteBakeryUseCase;
import com.breaditnow.owner.bakery.application.port.in.UpdateBakeryUseCase;
import com.breaditnow.owner.bakery.application.port.out.AddressPort;
import com.breaditnow.owner.bakery.application.port.out.BakeryRepository;
import com.breaditnow.owner.bakery.application.port.out.ImagePort;
import com.breaditnow.owner.bakery.domain.Address;
import com.breaditnow.owner.bakery.domain.Bakery;
import com.breaditnow.owner.bakery.domain.Image;
import com.breaditnow.owner.bakery.domain.PhoneNumber;
import com.breaditnow.owner.bakery.infrastructure.external.api.AddressInfo;
import com.breaditnow.owner.bakery.infrastructure.presentation.request.BakeryCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import static com.breaditnow.domain.global.exception.DomainErrorCode.BAKERY_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class BakeryManagementService implements CreateBakeryUseCase, DeleteBakeryUseCase, UpdateBakeryUseCase {
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
    public void deleteBakery(Long ownerId, Long bakeryId) {
        Bakery bakery = getBakery(bakeryId);
        bakery.delete(ownerId);
        bakeryRepository.save(bakery);
    }

    private Bakery getBakery(Long bakeryId) {
        return bakeryRepository.findById(bakeryId)
                .orElseThrow(() -> new DomainException(BAKERY_NOT_FOUND));
    }
}