package com.breaditnow.owner.bakery.application.port.out;

import com.breaditnow.owner.bakery.domain.Image;
import org.springframework.web.multipart.MultipartFile;

public interface ImagePort {
    Image saveImage(MultipartFile imageFile);
}
