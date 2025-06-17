package com.breaditnow.owner.bakery.application.port.in;

import com.breaditnow.owner.bakery.infrastructure.presentation.request.BakeryCreateRequest;
import org.springframework.web.multipart.MultipartFile;

public interface CreateBakeryUseCase {
    Long createBakery(Long ownerId, BakeryCreateRequest request, MultipartFile profileImage);
}
