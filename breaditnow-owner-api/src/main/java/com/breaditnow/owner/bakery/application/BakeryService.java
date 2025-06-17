package com.breaditnow.owner.bakery.application;

import com.breaditnow.domain.global.exception.DomainException;
import com.breaditnow.owner.bakery.application.port.ImageStorageService;
import com.breaditnow.owner.bakery.domain.Address;
import com.breaditnow.owner.bakery.domain.Bakery;
import com.breaditnow.owner.bakery.domain.Image;
import com.breaditnow.owner.bakery.domain.PhoneNumber;
import com.breaditnow.owner.bakery.domain.port.BakeryRepository;
import com.breaditnow.owner.bakery.infrastructure.external.api.AddressInfo;
import com.breaditnow.owner.bakery.infrastructure.external.api.AddressPort;
import com.breaditnow.owner.bakery.presentation.request.BakeryCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import static com.breaditnow.domain.global.exception.DomainErrorCode.BAKERY_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class BakeryService {
    private final ImageStorageService imageStorageService;
    private final BakeryRepository bakeryRepository;
    private final AddressPort addressPort;

    @Transactional
    public Long createBakery(Long ownerId, BakeryCreateRequest request, MultipartFile profileImage) {
        AddressInfo addressInfo = addressPort.getAddressInfo(request.address());
        Address address = Address.create(addressInfo.regionCode(), request.address(), addressInfo.latitude(), addressInfo.longitude());
        Image image = imageStorageService.saveImage(profileImage);
        PhoneNumber phoneNumber = PhoneNumber.create(request.phoneNumber());
        Bakery bakery = Bakery.create(ownerId, request.name(), address, phoneNumber, image, request.openTime(), request.introduction());
        return bakeryRepository.save(bakery);
    }

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