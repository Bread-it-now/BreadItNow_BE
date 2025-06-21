package com.breaditnow.owner.image.application.port.in;

import com.breaditnow.owner.bakery.domain.Image;
import org.springframework.web.multipart.MultipartFile;

public interface ImagePort {
    Image saveImage(MultipartFile imageFile);
}
