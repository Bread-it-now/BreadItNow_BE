package com.breaditnow.owner.bakery.application;

import com.breaditnow.owner.bakery.application.port.ImageStorageService;
import com.breaditnow.owner.bakery.presentation.request.BakeryCreateRequest;
import com.breaditnow.owner.bakery.domain.Address;
import com.breaditnow.owner.bakery.domain.Bakery;
import com.breaditnow.owner.bakery.domain.Image;
import com.breaditnow.owner.bakery.domain.PhoneNumber;
import com.breaditnow.owner.bakery.domain.port.BakeryRepository;
import com.breaditnow.owner.bakery.infrastructure.external.api.AddressInfo;
import com.breaditnow.owner.bakery.infrastructure.external.api.AddressPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class BakeryServiceImpl {
    private final ImageStorageService imageStorageService;
    private final BakeryRepository bakeryRepository;
    private final AddressPort addressPort;

    @Transactional
    public Long createBakery(Long ownerId, BakeryCreateRequest request, MultipartFile profileImage) {
        AddressInfo addressInfo = addressPort.getAddressInfo(request.address());
        Address address = Address.create(addressInfo.regionCode(), request.address(), addressInfo.latitude(), addressInfo.longitude());
        Image image = imageStorageService.saveImage(profileImage);
        PhoneNumber phoneNumber = PhoneNumber.create(request.phoneNumber());
        Bakery bakery = new Bakery(ownerId, request.name(), address, phoneNumber, request.openTime(), request.introduction(), image);
        return bakeryRepository.saveBakery(bakery);
    }
}