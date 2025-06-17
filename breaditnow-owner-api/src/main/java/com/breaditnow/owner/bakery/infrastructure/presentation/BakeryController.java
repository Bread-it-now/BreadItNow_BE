package com.breaditnow.owner.bakery.infrastructure.presentation;

import com.breaditnow.common.response.ApiSuccessResponse;
import com.breaditnow.owner.bakery.application.port.in.*;
import com.breaditnow.owner.bakery.application.port.in.queries.GetBakeryDetailsUseCase;
import com.breaditnow.owner.bakery.domain.Bakery;
import com.breaditnow.owner.bakery.infrastructure.presentation.request.BakeryCreateRequest;
import com.breaditnow.owner.bakery.infrastructure.presentation.request.BakeryUpdateRequest;
import com.breaditnow.owner.bakery.infrastructure.presentation.request.OperatingStatusUpdateRequest;
import com.breaditnow.owner.bakery.infrastructure.presentation.response.BakeryResponse;
import com.breaditnow.owner.global.security.annotation.AuthOwner;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

@RestController
@RequestMapping("/api/v1/bakery")
@RequiredArgsConstructor
public class BakeryController {
    private final CreateBakeryUseCase createBakeryUseCase;
    private final DeleteBakeryUseCase deleteBakeryUseCase;
    private final UpdateBakeryUseCase updateBakeryUseCase;
    private final UpdateOperatingStatusUseCase updateOperatingStatusUseCase;
    private final UpdateProfileImageUseCase updateProfileImageUseCase;
    private final AddBakeryImagesUseCase addBakeryImagesUseCase;
    private final GetBakeryDetailsUseCase getBakeryDetailsUseCase;

    @PostMapping(consumes = {MULTIPART_FORM_DATA_VALUE})
    public ApiSuccessResponse<Map<String, Long>> createBakery(
            @AuthOwner Long ownerId,
            @RequestPart(value = "data") BakeryCreateRequest request,
            @RequestPart(value = "profileImage", required = false) MultipartFile profileImage
    ){
        return ApiSuccessResponse.of("bakeryId", createBakeryUseCase.createBakery(ownerId, request, profileImage));
    }

    @PutMapping("/{bakeryId}")
    public ApiSuccessResponse<Void> updateBakery(
            @AuthOwner Long ownerId,
            @PathVariable("bakeryId") Long bakeryId,
            @RequestBody BakeryUpdateRequest request
    ) {
        updateBakeryUseCase.updateBakery(ownerId, bakeryId, request);
        return ApiSuccessResponse.of();
    }

    @DeleteMapping("/{bakeryId}")
    public ApiSuccessResponse<Void> deleteBakery(@AuthOwner Long ownerId, @PathVariable(name = "bakeryId") Long bakeryId) {
        deleteBakeryUseCase.deleteBakery(ownerId, bakeryId);
        return ApiSuccessResponse.of();
    }

    @PatchMapping("/{bakeryId}/operating-status")
    public ApiSuccessResponse<Void> updateOperatingStatus(
            @AuthOwner Long ownerId,
            @PathVariable("bakeryId") Long bakeryId,
            @RequestBody OperatingStatusUpdateRequest request
    ) {
        updateOperatingStatusUseCase.updateOperatingStatus(ownerId, bakeryId, request.operatingStatus());
        return ApiSuccessResponse.of();
    }

    @PostMapping(value = "/{bakeryId}/profile-image", consumes = {MULTIPART_FORM_DATA_VALUE})
    public ApiSuccessResponse<Void> updateProfileImage(
            @AuthOwner Long ownerId,
            @PathVariable("bakeryId") Long bakeryId,
            @RequestPart("profileImage") MultipartFile profileImage
    ) {
        updateProfileImageUseCase.updateProfileImage(ownerId, bakeryId, profileImage);
        return ApiSuccessResponse.of();
    }

    @PostMapping(value = "/{bakeryId}/additional-images", consumes = {MULTIPART_FORM_DATA_VALUE})
    public ApiSuccessResponse<Void> addAdditionalImages(
            @AuthOwner Long ownerId,
            @PathVariable("bakeryId") Long bakeryId,
            @RequestPart("additionalImages") List<MultipartFile> images
    ) {
        addBakeryImagesUseCase.addAdditionalImages(ownerId, bakeryId, images);
        return ApiSuccessResponse.of();
    }

    @GetMapping("/{bakeryId}")
    public ApiSuccessResponse<BakeryResponse> getBakery(@PathVariable("bakeryId") Long bakeryId) {
        Bakery bakery = getBakeryDetailsUseCase.getBakeryDetails(bakeryId);
        return ApiSuccessResponse.of(BakeryResponse.from(bakery));
    }
}
