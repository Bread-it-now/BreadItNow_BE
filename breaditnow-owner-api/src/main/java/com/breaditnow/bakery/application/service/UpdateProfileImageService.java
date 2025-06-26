package com.breaditnow.bakery.application.service;

import com.breaditnow.bakery.domain.port.in.UpdateProfileImageUseCase;
import com.breaditnow.bakery.domain.port.out.BakeryRepository;
import com.breaditnow.bakery.domain.model.Bakery;
import com.breaditnow.bakery.domain.model.Image;
import com.breaditnow.common.support.RepositorySupport;
import com.breaditnow.image.application.port.in.ImagePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class UpdateProfileImageService implements UpdateProfileImageUseCase {
    private final BakeryRepository bakeryRepository;
    private final ImagePort imagePort;

    @Override
    @Transactional
    public void updateProfileImage(Long ownerId, Long bakeryId, MultipartFile newProfileImage) {
        Bakery bakery = RepositorySupport.findBakeryOrElseThrow(bakeryRepository, bakeryId);
        Image newImage = imagePort.saveImage(newProfileImage);
        bakery.updateProfileImage(ownerId, newImage);
        bakeryRepository.save(bakery);
    }
}
