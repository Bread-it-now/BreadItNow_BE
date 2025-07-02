package com.breaditnow.bakery.application.facade;

import com.breaditnow.bakery.application.dto.request.BakeryCreateRequest;
import com.breaditnow.bakery.application.dto.request.BakeryUpdateRequest;
import com.breaditnow.bakery.application.dto.response.BakeryResponse;
import com.breaditnow.bakery.domain.model.Bakery;
import com.breaditnow.common.domain.OperatingStatus;
import com.breaditnow.bakery.domain.port.in.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Component
@RequiredArgsConstructor
public class BakeryFacade {
    private final CreateBakeryUseCase createBakeryUseCase;
    private final DeleteBakeryUseCase deleteBakeryUseCase;
    private final UpdateBakeryUseCase updateBakeryUseCase;
    private final UpdateOperatingStatusUseCase updateOperatingStatusUseCase;
    private final UpdateProfileImageUseCase updateProfileImageUseCase;
    private final AddBakeryImagesUseCase addBakeryImagesUseCase;
    private final GetBakeryDetailsUseCase getBakeryDetailsUseCase;

    public Long createBakery(Long ownerId, BakeryCreateRequest request, MultipartFile profileImage) {
        return createBakeryUseCase.createBakery(ownerId, request, profileImage);
    }

    public void updateBakery(Long ownerId, Long bakeryId, BakeryUpdateRequest request) {
        updateBakeryUseCase.updateBakery(ownerId, bakeryId, request);
    }

    @Transactional(readOnly = true)
    public BakeryResponse getBakeryDetails(Long bakeryId) {
        Bakery bakery = getBakeryDetailsUseCase.getBakeryDetails(bakeryId);
        return BakeryResponse.from(bakery);
    }

    public void deleteBakery(Long ownerId, Long bakeryId) {
        deleteBakeryUseCase.deleteBakery(ownerId, bakeryId);
    }

    public void updateOperatingStatus(Long ownerId, Long bakeryId, OperatingStatus newStatus) {
        updateOperatingStatusUseCase.updateOperatingStatus(ownerId, bakeryId, newStatus);
    }

    public void updateProfileImage(Long ownerId, Long bakeryId, MultipartFile profileImage) {
        updateProfileImageUseCase.updateProfileImage(ownerId, bakeryId, profileImage);
    }

    public void addAdditionalImages(Long ownerId, Long bakeryId, List<MultipartFile> images) {
        addBakeryImagesUseCase.addAdditionalImages(ownerId, bakeryId, images);
    }
}
