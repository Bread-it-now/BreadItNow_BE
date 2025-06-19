package com.breaditnow.owner.product.infrastructure.presentation;

import com.breaditnow.common.response.ApiSuccessResponse;
import com.breaditnow.owner.global.security.annotation.AuthOwner;
import com.breaditnow.owner.product.application.port.in.CreateProductUseCase;
import com.breaditnow.owner.product.application.port.in.UpdateProductStatusUseCase;
import com.breaditnow.owner.product.application.port.in.UpdateProductStockUseCase;
import com.breaditnow.owner.product.application.port.in.UpdateProductUseCase;
import com.breaditnow.owner.product.infrastructure.presentation.request.ProductCreateRequest;
import com.breaditnow.owner.product.infrastructure.presentation.request.ProductStatusUpdateRequest;
import com.breaditnow.owner.product.infrastructure.presentation.request.ProductStockUpdateRequest;
import com.breaditnow.owner.product.infrastructure.presentation.request.ProductUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/bakery/{bakeryId}/product")
@RequiredArgsConstructor
public class ProductController {
    private final CreateProductUseCase createProductUseCase;
    private final UpdateProductUseCase updateProductUseCase;
    private final UpdateProductStockUseCase updateProductStockUseCase;
    private final UpdateProductStatusUseCase updateProductStatusUseCase;

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

    @PutMapping(value = "/{productId}", consumes = "multipart/form-data")
    public ApiSuccessResponse<Map<String, Long>> updateBakeryProduct(
            @AuthOwner Long ownerId,
            @PathVariable("bakeryId") Long bakeryId,
            @PathVariable("productId") Long productId,
            @RequestPart("data") ProductUpdateRequest request,
            @RequestPart(value = "productImage", required = false) MultipartFile productImage
    ) {
        updateProductUseCase.updateProduct(ownerId, bakeryId, productId, request, productImage);
        return ApiSuccessResponse.of("productId", productId);
    }

    @PatchMapping("/{productId}/stock")
    public ApiSuccessResponse<Void> updateProductStock(
            @AuthOwner Long ownerId,
            @PathVariable("bakeryId") Long bakeryId,
            @PathVariable("productId") Long productId,
            @RequestBody ProductStockUpdateRequest request
    ) {
        updateProductStockUseCase.updateProductStock(ownerId, bakeryId, productId, request);
        return ApiSuccessResponse.of();
    }

    @PatchMapping("/{productId}/status")
    public ApiSuccessResponse<Void> updateProductStatus(
            @AuthOwner Long ownerId,
            @PathVariable("bakeryId") Long bakeryId,
            @PathVariable("productId") Long productId,
            @RequestBody ProductStatusUpdateRequest request
    ) {
        updateProductStatusUseCase.updateProductStatus(ownerId, bakeryId, productId, request);
        return ApiSuccessResponse.of();
    }
}
