package com.breaditnow.image.application.port.in;

import com.breaditnow.bakery.domain.model.Image;
import org.springframework.web.multipart.MultipartFile;

public interface ImagePort {
    Image saveImage(MultipartFile imageFile);
}
