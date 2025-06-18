package com.breaditnow.owner.product.infrastructure.presentation;

import com.breaditnow.common.response.ApiSuccessResponse;
import com.breaditnow.owner.global.security.annotation.AuthOwner;
import com.breaditnow.owner.product.application.port.in.CreateProductUseCase;
import com.breaditnow.owner.product.infrastructure.presentation.request.ProductCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/bakery/{bakeryId}/product")
@RequiredArgsConstructor
public class ProductController {
    private final CreateProductUseCase createProductUseCase;

    @PostMapping(consumes = "multipart/form-data")
    public ApiSuccessResponse<Map<String, Long>> createBakeryProduct(
            @AuthOwner Long ownerId,
            @PathVariable("bakeryId") Long bakeryId,
            @RequestPart("data") ProductCreateRequest request,
            @RequestPart(value = "productImage", required = false) MultipartFile productImage
    ) {
        Long productId = createProductUseCase.createProduct(ownerId, bakeryId, request, productImage);
        return ApiSuccessResponse.of("productId", productId);
    }
}
