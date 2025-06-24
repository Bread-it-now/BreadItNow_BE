package com.breaditnow.owner.bakery.application.service;

import com.breaditnow.owner.bakery.application.port.in.UpdateProfileImageUseCase;
import com.breaditnow.owner.bakery.application.port.out.BakeryRepository;
import com.breaditnow.owner.bakery.domain.Bakery;
import com.breaditnow.owner.bakery.domain.Image;
import com.breaditnow.owner.image.application.port.in.ImagePort;
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
        Bakery bakery = bakeryRepository.getById(bakeryId);
        Image newImage = imagePort.saveImage(newProfileImage);
        bakery.updateProfileImage(ownerId, newImage);
        bakeryRepository.save(bakery);
    }
}
