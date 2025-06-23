package com.breaditnow.owner.bakery.application.service;

import com.breaditnow.owner.bakery.application.port.in.AddBakeryImagesUseCase;
import com.breaditnow.owner.bakery.application.port.out.BakeryRepository;
import com.breaditnow.owner.bakery.application.port.out.PublishBakeryEventPort;
import com.breaditnow.owner.bakery.domain.Bakery;
import com.breaditnow.owner.bakery.domain.Image;
import com.breaditnow.owner.image.application.port.in.ImagePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AddBakeryImagesService implements AddBakeryImagesUseCase {
    private final BakeryRepository bakeryRepository;
    private final ImagePort imagePort;
    private final PublishBakeryEventPort publishBakeryEventPort;

    @Override
    @Transactional
    public void addAdditionalImages(Long ownerId, Long bakeryId, List<MultipartFile> images) {
        Bakery bakery = bakeryRepository.getById(bakeryId);

        List<Image> newImages = images.stream()
                .map(imagePort::saveImage)
                .collect(Collectors.toList());

        bakery.addAdditionalImages(ownerId, newImages);
        bakeryRepository.save(bakery);
        publishBakeryEventPort.publishBakeryUpdatedEvent(bakery);
    }
}
