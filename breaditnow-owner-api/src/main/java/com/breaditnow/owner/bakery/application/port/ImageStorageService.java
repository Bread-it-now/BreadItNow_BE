package com.breaditnow.owner.bakery.application.port;

import com.breaditnow.owner.bakery.domain.Image;
import org.springframework.web.multipart.MultipartFile;

public interface ImageStorageService {
    Image saveImage(MultipartFile imageFile);
}
