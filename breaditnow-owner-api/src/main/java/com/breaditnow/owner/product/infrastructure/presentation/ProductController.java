package com.breaditnow.owner.product.infrastructure.presentation;

import com.breaditnow.common.response.ApiSuccessResponse;
import com.breaditnow.owner.global.security.annotation.AuthOwner;
import com.breaditnow.owner.product.application.port.in.*;
import com.breaditnow.owner.product.infrastructure.presentation.request.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/bakery/{bakeryId}")
@RequiredArgsConstructor
public class ProductController {
    private final CreateProductUseCase createProductUseCase;
    private final UpdateProductUseCase updateProductUseCase;
    private final UpdateProductStockUseCase updateProductStockUseCase;
    private final UpdateProductStatusUseCase updateProductStatusUseCase;
    private final UpdateProductsStatusUseCase updateProductsStatusUseCase;

    @PostMapping(value = "/product", consumes = "multipart/form-data")
    public ApiSuccessResponse<Map<String, Long>> createBakeryProduct(
            @AuthOwner Long ownerId,
            @PathVariable("bakeryId") Long bakeryId,
            @RequestPart("data") ProductCreateRequest request,
            @RequestPart(value = "productImage", required = false) MultipartFile productImage
    ) {
        Long productId = createProductUseCase.createProduct(ownerId, bakeryId, request, productImage);
        return ApiSuccessResponse.of("productId", productId);
    }

    @PutMapping(value = "/product/{productId}", consumes = "multipart/form-data")
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

    @PatchMapping("/product/{productId}/stock")
    public ApiSuccessResponse<Void> updateProductStock(
            @AuthOwner Long ownerId,
            @PathVariable("bakeryId") Long bakeryId,
            @PathVariable("productId") Long productId,
            @RequestBody ProductStockUpdateRequest request
    ) {
        updateProductStockUseCase.updateProductStock(ownerId, bakeryId, productId, request);
        return ApiSuccessResponse.of();
    }

    @PatchMapping("/product/{productId}/status")
    public ApiSuccessResponse<Void> updateProductStatus(
            @AuthOwner Long ownerId,
            @PathVariable("bakeryId") Long bakeryId,
            @PathVariable("productId") Long productId,
            @RequestBody ProductStatusUpdateRequest request
    ) {
        updateProductStatusUseCase.updateProductStatus(ownerId, bakeryId, productId, request);
        return ApiSuccessResponse.of();
    }

    @PatchMapping("/products/status")
    public ApiSuccessResponse<Void> updateProductsStatus(
            @AuthOwner Long ownerId,
            @PathVariable("bakeryId") Long bakeryId,
            @RequestBody ProductsStatusUpdateRequest request
    ) {
        updateProductsStatusUseCase.updateProductsStatus(ownerId, bakeryId, request);
        return ApiSuccessResponse.of();
    }
}
