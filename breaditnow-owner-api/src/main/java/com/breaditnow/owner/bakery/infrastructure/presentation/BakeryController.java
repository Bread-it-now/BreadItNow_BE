package com.breaditnow.owner.bakery.infrastructure.presentation;

import com.breaditnow.common.response.ApiSuccessResponse;
import com.breaditnow.owner.bakery.application.port.in.CreateBakeryUseCase;
import com.breaditnow.owner.bakery.application.port.in.DeleteBakeryUseCase;
import com.breaditnow.owner.bakery.infrastructure.presentation.request.BakeryCreateRequest;
import com.breaditnow.owner.global.security.annotation.AuthOwner;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

@RestController
@RequestMapping("/api/v1/bakery")
@RequiredArgsConstructor
public class BakeryController {
    private final CreateBakeryUseCase createBakeryUseCase;
    private final DeleteBakeryUseCase deleteBakeryUseCase;

    @PostMapping(consumes = {MULTIPART_FORM_DATA_VALUE})
    public ApiSuccessResponse<Map<String, Long>> createBakery(
            @AuthOwner Long ownerId,
            @RequestPart(value = "data") BakeryCreateRequest request,
            @RequestPart(value = "profileImage", required = false) MultipartFile profileImage
    ){
        return ApiSuccessResponse.of("bakeryId", createBakeryUseCase.createBakery(ownerId, request, profileImage));
    }

    @DeleteMapping("/{bakeryId}")
    public ApiSuccessResponse<Void> deleteBakery(@AuthOwner Long ownerId, @PathVariable(name = "bakeryId") Long bakeryId) {
        deleteBakeryUseCase.deleteBakery(ownerId, bakeryId);
        return ApiSuccessResponse.of();
    }
}
