package com.breaditnow.product.adapter.in.web;

import com.breaditnow.common.response.ApiSuccessResponse;
import com.breaditnow.common.security.annotation.AuthOwner;
import com.breaditnow.product.application.dto.request.*;
import com.breaditnow.product.application.dto.response.ProductResponse;
import com.breaditnow.product.domain.port.in.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
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
    private final UpdateProductDisplayOrderUseCase updateProductDisplayOrderUseCase;
    private final DeleteProductUseCase deleteProductUseCase;
    private final DeleteProductsUseCase deleteProductsUseCase;
    private final GetProductUseCase getProductUseCase;
    private final ListProductsUseCase listProductsUseCase;

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

    @PatchMapping("/products/order")
    public ApiSuccessResponse<Void> updateProductDisplayOrder(
            @AuthOwner Long ownerId,
            @PathVariable("bakeryId") Long bakeryId,
            @RequestBody ProductDisplayOrderUpdateRequest request
    ) {
        updateProductDisplayOrderUseCase.updateProductDisplayOrder(ownerId, bakeryId, request);
        return ApiSuccessResponse.of();
    }

    @DeleteMapping("/product/{productId}")
    public ApiSuccessResponse<Void> deleteProduct(
            @AuthOwner Long ownerId,
            @PathVariable("bakeryId") Long bakeryId,
            @PathVariable("productId") Long productId
    ) {
        deleteProductUseCase.deleteProduct(ownerId, bakeryId, productId);
        return ApiSuccessResponse.of();
    }

    @DeleteMapping("/products")
    public ApiSuccessResponse<Void> deleteProducts(
            @AuthOwner Long ownerId,
            @PathVariable("bakeryId") Long bakeryId,
            @RequestBody ProductsDeleteRequest request
    ) {
        deleteProductsUseCase.deleteProducts(ownerId, bakeryId, request);
        return ApiSuccessResponse.of();
    }

    @GetMapping("/product/{productId}")
    public ApiSuccessResponse<ProductResponse> getProductDetail(
            @AuthOwner Long ownerId,
            @PathVariable("bakeryId") Long bakeryId,
            @PathVariable("productId") Long productId
    ) {
        return ApiSuccessResponse.of(getProductUseCase.getProductDetail(ownerId, bakeryId, productId));
    }

    @GetMapping("/products")
    public ApiSuccessResponse<List<ProductResponse>> listProducts(
            @AuthOwner Long ownerId,
            @PathVariable("bakeryId") Long bakeryId,
            @RequestParam(value = "type", required = false) String type,
            @RequestParam(value = "status", required = false) String status
    ) {
        ProductSearchCondition condition = new ProductSearchCondition(status, type);
        return ApiSuccessResponse.of(listProductsUseCase.listProducts(ownerId, bakeryId, condition));
    }
}
