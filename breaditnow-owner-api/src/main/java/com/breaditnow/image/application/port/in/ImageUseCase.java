package com.breaditnow.image.application.port.in;

import com.breaditnow.image.domain.Image;
import org.springframework.web.multipart.MultipartFile;

public interface ImageUseCase {
    Image saveImage(MultipartFile imageFile);
}
