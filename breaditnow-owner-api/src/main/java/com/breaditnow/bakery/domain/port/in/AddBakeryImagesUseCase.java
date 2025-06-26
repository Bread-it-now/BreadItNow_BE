package com.breaditnow.bakery.domain.port.in;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface AddBakeryImagesUseCase {
    void addAdditionalImages(Long ownerId, Long bakeryId, List<MultipartFile> images);
}
