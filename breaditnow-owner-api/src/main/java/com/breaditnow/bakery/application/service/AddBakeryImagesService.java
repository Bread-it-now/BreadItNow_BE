package com.breaditnow.bakery.application.service;

import com.breaditnow.bakery.domain.model.Bakery;
import com.breaditnow.bakery.domain.model.Image;
import com.breaditnow.bakery.domain.port.in.AddBakeryImagesUseCase;
import com.breaditnow.bakery.domain.port.out.BakeryRepository;
import com.breaditnow.common.support.RepositorySupport;
import com.breaditnow.image.application.port.in.ImagePort;
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

    @Override
    @Transactional
    public void addAdditionalImages(Long ownerId, Long bakeryId, List<MultipartFile> images) {
        Bakery bakery = RepositorySupport.findBakeryOrElseThrow(bakeryRepository, bakeryId);

        List<Image> newImages = images.stream()
                .map(imagePort::saveImage)
                .collect(Collectors.toList());

        bakery.addAdditionalImages(ownerId, newImages);
        bakeryRepository.save(bakery);
    }
}
