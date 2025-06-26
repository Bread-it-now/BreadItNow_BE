package com.breaditnow.bakery.adapter.in.web;

import com.breaditnow.bakery.application.dto.request.BakeryCreateRequest;
import com.breaditnow.bakery.application.dto.request.BakeryUpdateRequest;
import com.breaditnow.bakery.application.dto.request.OperatingStatusUpdateRequest;
import com.breaditnow.bakery.application.dto.response.BakeryResponse;
import com.breaditnow.bakery.application.facade.BakeryFacade;
import com.breaditnow.common.response.ApiSuccessResponse;
import com.breaditnow.common.security.annotation.AuthOwner;
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
    private final BakeryFacade bakeryFacade;

    @PostMapping(consumes = {MULTIPART_FORM_DATA_VALUE})
    public ApiSuccessResponse<Map<String, Long>> createBakery(
            @AuthOwner Long ownerId,
            @RequestPart(value = "data") BakeryCreateRequest request,
            @RequestPart(value = "profileImage", required = false) MultipartFile profileImage
    ){
        return ApiSuccessResponse.of("bakeryId", bakeryFacade.createBakery(ownerId, request, profileImage));
    }

    @PutMapping("/{bakeryId}")
    public ApiSuccessResponse<Void> updateBakery(
            @AuthOwner Long ownerId,
            @PathVariable("bakeryId") Long bakeryId,
            @RequestBody BakeryUpdateRequest request
    ) {
        bakeryFacade.updateBakery(ownerId, bakeryId, request);
        return ApiSuccessResponse.of();
    }

    @DeleteMapping("/{bakeryId}")
    public ApiSuccessResponse<Void> deleteBakery(@AuthOwner Long ownerId, @PathVariable(name = "bakeryId") Long bakeryId) {
        bakeryFacade.deleteBakery(ownerId, bakeryId);
        return ApiSuccessResponse.of();
    }

    @PatchMapping("/{bakeryId}/operating-status")
    public ApiSuccessResponse<Void> updateOperatingStatus(
            @AuthOwner Long ownerId,
            @PathVariable("bakeryId") Long bakeryId,
            @RequestBody OperatingStatusUpdateRequest request
    ) {
        bakeryFacade.updateOperatingStatus(ownerId, bakeryId, request.operatingStatus());
        return ApiSuccessResponse.of();
    }

    @PostMapping(value = "/{bakeryId}/profile-image", consumes = {MULTIPART_FORM_DATA_VALUE})
    public ApiSuccessResponse<Void> updateProfileImage(
            @AuthOwner Long ownerId,
            @PathVariable("bakeryId") Long bakeryId,
            @RequestPart("profileImage") MultipartFile profileImage
    ) {
        bakeryFacade.updateProfileImage(ownerId, bakeryId, profileImage);
        return ApiSuccessResponse.of();
    }

    @PostMapping(value = "/{bakeryId}/additional-images", consumes = {MULTIPART_FORM_DATA_VALUE})
    public ApiSuccessResponse<Void> addAdditionalImages(
            @AuthOwner Long ownerId,
            @PathVariable("bakeryId") Long bakeryId,
            @RequestPart("additionalImages") List<MultipartFile> images
    ) {
        bakeryFacade.addAdditionalImages(ownerId, bakeryId, images);
        return ApiSuccessResponse.of();
    }

    @GetMapping("/{bakeryId}")
    public ApiSuccessResponse<BakeryResponse> getBakery(@PathVariable("bakeryId") Long bakeryId) {
        return ApiSuccessResponse.of(bakeryFacade.getBakeryDetails(bakeryId));
    }
}
