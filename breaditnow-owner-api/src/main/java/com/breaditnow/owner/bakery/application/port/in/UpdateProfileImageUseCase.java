package com.breaditnow.owner.bakery.application.port.in;

import org.springframework.web.multipart.MultipartFile;

public interface UpdateProfileImageUseCase {
    void updateProfileImage(Long ownerId, Long bakeryId, MultipartFile newProfileImage);
}
