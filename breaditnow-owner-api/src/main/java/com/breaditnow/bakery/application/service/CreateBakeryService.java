package com.breaditnow.bakery.application.service;

import com.breaditnow.bakery.application.event.BakeryCreatedEvent;
import com.breaditnow.bakery.domain.port.in.CreateBakeryUseCase;
import com.breaditnow.bakery.domain.port.out.BakeryRepository;
import com.breaditnow.bakery.domain.port.out.BakeryEventPublisherPort;
import com.breaditnow.location.domain.model.Address;
import com.breaditnow.bakery.domain.model.Bakery;
import com.breaditnow.image.domain.Image;
import com.breaditnow.bakery.domain.model.PhoneNumber;
import com.breaditnow.bakery.adapter.in.web.dto.request.BakeryCreateRequest;
import com.breaditnow.image.application.port.in.ImageUseCase;
import com.breaditnow.location.domain.port.out.AddressPort;
import com.breaditnow.location.application.dto.AddressInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class CreateBakeryService implements CreateBakeryUseCase {
    private final ImageUseCase imageUseCase;
    private final AddressPort addressPort;
    private final BakeryEventPublisherPort bakeryEventPublisherPort;
    private final BakeryRepository bakeryRepository;

    @Override
    @Transactional
    public Long createBakery(Long ownerId, BakeryCreateRequest request, MultipartFile profileImage) {
        AddressInfo addressInfo = addressPort.getAddressInfo(request.address());
        Address address = Address.create(addressInfo.regionCode(), request.address(), addressInfo.latitude(), addressInfo.longitude());
        Image image = imageUseCase.saveImage(profileImage);
        PhoneNumber phoneNumber = PhoneNumber.create(request.phoneNumber());
        Bakery bakery = Bakery.create(ownerId, request.name(), address, phoneNumber, image, request.openTime(), request.introduction());
        Bakery savedBakery = bakeryRepository.save(bakery);

        BakeryCreatedEvent event = BakeryCreatedEvent.from(savedBakery);
        bakeryEventPublisherPort.publishBakeryCreatedEvent(event);

        return savedBakery.getBakeryId();
    }
}
