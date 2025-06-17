package com.breaditnow.owner.bakery.application;

import com.breaditnow.owner.bakery.application.port.ImageStorageService;
import com.breaditnow.owner.bakery.domain.Image;
import com.breaditnow.owner.bakery.infrastructure.external.api.StoragePort;
import com.breaditnow.owner.global.exception.OwnerException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static com.breaditnow.owner.global.exception.OwnerErrorCode.IMAGE_UPLOAD_FAILED;
import static com.breaditnow.owner.global.exception.OwnerErrorCode.IMAGE_UPLOAD_FAILED_WITH_ERROR;

@Service
@RequiredArgsConstructor
public class ImageStorageServiceImpl implements ImageStorageService {
    private final StoragePort storagePort;

    @Override
    public Image saveImage(MultipartFile imageFile) {
        if (imageFile == null || imageFile.isEmpty()) {
            return null;
        }

        try {
            byte[] imageBytes = imageFile.getBytes();
            String originalFilename = imageFile.getOriginalFilename();
            String imageUrl = storagePort.uploadImage(imageBytes, originalFilename);
            return Image.create(imageUrl);
        } catch (IOException e) {
            throw new OwnerException(IMAGE_UPLOAD_FAILED);
        } catch (Exception e) {
            throw new OwnerException(IMAGE_UPLOAD_FAILED_WITH_ERROR);
        }
    }
}
