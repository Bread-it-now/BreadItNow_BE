package com.breaditnow.image.application;

import com.breaditnow.image.application.port.in.ImageUseCase;
import com.breaditnow.image.domain.Image;
import com.breaditnow.image.application.port.out.StoragePort;
import com.breaditnow.common.exception.OwnerException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static com.breaditnow.common.exception.OwnerErrorCode.IMAGE_UPLOAD_FAILED;
import static com.breaditnow.common.exception.OwnerErrorCode.IMAGE_UPLOAD_FAILED_WITH_ERROR;

@Service
@RequiredArgsConstructor
public class ImageService implements ImageUseCase {
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
