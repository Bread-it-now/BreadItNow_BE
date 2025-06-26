package com.breaditnow.bakery.domain.port.in;

import com.breaditnow.bakery.adapter.in.web.dto.request.BakeryCreateRequest;
import org.springframework.web.multipart.MultipartFile;

public interface CreateBakeryUseCase {
    Long createBakery(Long ownerId, BakeryCreateRequest request, MultipartFile profileImage);
}
