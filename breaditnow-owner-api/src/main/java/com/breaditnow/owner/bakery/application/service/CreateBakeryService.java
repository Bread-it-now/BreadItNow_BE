package com.breaditnow.owner.bakery.application.service;

import com.breaditnow.owner.bakery.application.dto.event.BakeryCreatedEvent;
import com.breaditnow.owner.bakery.application.port.in.CreateBakeryUseCase;
import com.breaditnow.owner.bakery.application.port.out.BakeryRepository;
import com.breaditnow.owner.bakery.application.port.out.PublishBakeryEventPort;
import com.breaditnow.owner.bakery.domain.Address;
import com.breaditnow.owner.bakery.domain.Bakery;
import com.breaditnow.owner.bakery.domain.Image;
import com.breaditnow.owner.bakery.domain.PhoneNumber;
import com.breaditnow.owner.bakery.infrastructure.adapter.in.presentation.request.BakeryCreateRequest;
import com.breaditnow.owner.image.application.port.in.ImagePort;
import com.breaditnow.owner.location.application.port.out.AddressPort;
import com.breaditnow.owner.location.infrastructure.AddressInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class CreateBakeryService implements CreateBakeryUseCase {
    private final ImagePort imagePort;
    private final AddressPort addressPort;
    private final BakeryRepository bakeryRepository;
    private final PublishBakeryEventPort publishBakeryEventPort;

    @Override
    @Transactional
    public Long createBakery(Long ownerId, BakeryCreateRequest request, MultipartFile profileImage) {
        AddressInfo addressInfo = addressPort.getAddressInfo(request.address());
        Address address = Address.create(addressInfo.regionCode(), request.address(), addressInfo.latitude(), addressInfo.longitude());
        Image image = imagePort.saveImage(profileImage);
        PhoneNumber phoneNumber = PhoneNumber.create(request.phoneNumber());
        Bakery bakery = Bakery.create(ownerId, request.name(), address, phoneNumber, image, request.openTime(), request.introduction());
        Bakery savedBakery = bakeryRepository.save(bakery);

        BakeryCreatedEvent event = BakeryCreatedEvent.from(savedBakery);
        publishBakeryEventPort.publishBakeryCreatedEvent(event);

        return savedBakery.getBakeryId();
    }
}
