//package com.breaditnow.owner.domain.product.controller;
//
//import com.breaditnow.common.response.ApiSuccessResponse;
//import com.breaditnow.owner.domain.product.controller.req.*;
//import com.breaditnow.owner.domain.product.controller.res.ProductListResponse;
//import com.breaditnow.owner.domain.product.controller.res.ProductResponse;
//import com.breaditnow.owner.domain.product.service.ProductService;
//import com.breaditnow.owner.global.security.annotation.AuthOwner;
//import jakarta.validation.Valid;
//import lombok.RequiredArgsConstructor;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.util.Map;
//
//import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;
//
//@RestController
//@RequiredArgsConstructor
//@RequestMapping("/api/v1/bakery-product")
//public class ProductController implements ProductControllerDocs {
//
//    private final ProductService productService;
//
//    @PostMapping(value = "/{bakeryId}", consumes = {MULTIPART_FORM_DATA_VALUE})
//    public ApiSuccessResponse<Map<String, Long>> createBakeryProduct(
//            @AuthOwner Long ownerId,
//            @PathVariable("bakeryId") Long bakeryId,
//            @RequestPart("data") @Valid ProductCreateRequest request,
//            @RequestPart(value = "productImage", required = false) MultipartFile productImage
//    ) {
//        Long productId = productService.createProduct(ownerId, bakeryId, request, productImage);
//        return ApiSuccessResponse.of("productId", productId);
//    }
//
//    @PutMapping(value = "/{bakeryId}/product/{productId}", consumes = {MULTIPART_FORM_DATA_VALUE})
//    public ApiSuccessResponse<ProductResponse> updateBakeryProduct(
//            @AuthOwner Long ownerId,
//            @PathVariable("bakeryId") Long bakeryId,
//            @PathVariable("productId") Long productId,
//            @RequestPart("data") @Valid ProductUpdateRequest request,
//            @RequestPart(value = "productImage", required = false) MultipartFile productImage
//    ) {
//        ProductResponse updatedProductResponse = productService.updateProduct(ownerId, bakeryId, productId, request,
//                productImage);
//        return ApiSuccessResponse.of(updatedProductResponse);
//    }
//
//    @DeleteMapping("/{bakeryId}/product/{productId}")
//    public ApiSuccessResponse<Map<String, Long>> deleteBakeryProduct(
//            @AuthOwner Long ownerId,
//            @PathVariable("bakeryId") Long bakeryId,
//            @PathVariable("productId") Long productId
//    ) {
//        Long deletedProductId = productService.deleteProduct(ownerId, bakeryId, productId);
//        return ApiSuccessResponse.of("productId", deletedProductId);
//    }
//
//    @DeleteMapping("/{bakeryId}/products")
//    public ApiSuccessResponse<Map<String, Integer>> deleteBakeryProducts(
//            @AuthOwner Long ownerId,
//            @PathVariable("bakeryId") Long bakeryId,
//            @RequestBody @Valid ProductDeleteRequest productDeleteRequest
//    ) {
//        int deletedCount = productService.deleteProducts(ownerId, bakeryId, productDeleteRequest.productIds());
//        return ApiSuccessResponse.of("deletedCount", deletedCount);
//    }
//
//    @PatchMapping("/{bakeryId}/products/hide")
//    public ApiSuccessResponse<Map<String, Integer>> updateHiddenProducts(
//            @AuthOwner Long ownerId,
//            @PathVariable("bakeryId") Long bakeryId,
//            @RequestBody @Valid ProductHiddenRequest productHiddenRequest
//    ) {
//        productService.updateHiddenProducts(ownerId, bakeryId, productHiddenRequest.productIds(),
//                productHiddenRequest.hidden());
//        return ApiSuccessResponse.of();
//    }
//
//    @GetMapping("/{bakeryId}/product/{productId}")
//    public ApiSuccessResponse<ProductResponse> getBakeryProduct(
//            @AuthOwner Long ownerId,
//            @PathVariable("bakeryId") Long bakeryId,
//            @PathVariable("productId") Long productId
//    ) {
//        return ApiSuccessResponse.of(productService.getProduct(ownerId, bakeryId, productId));
//    }
//
//    @GetMapping("/{bakeryId}")
//    public ApiSuccessResponse<ProductListResponse> getBakeryProducts(
//            @AuthOwner Long ownerId,
//            @PathVariable("bakeryId") Long bakeryId
//    ) {
//        return ApiSuccessResponse.of(productService.getProducts(ownerId, bakeryId));
//    }
//
//    @PatchMapping("/{bakeryId}/order")
//    public ApiSuccessResponse<String> updateBakeryProductOrder(
//            @AuthOwner Long ownerId,
//            @PathVariable("bakeryId") Long bakeryId,
//            @RequestBody @Valid ProductOrderUpdateRequest orderUpdateRequest
//    ) {
//        productService.updateProductOrder(ownerId, bakeryId, orderUpdateRequest.productOrders());
//        return ApiSuccessResponse.of();
//    }
//
//    @PatchMapping("/{bakery_id}/product/{product_id}/stock")
//    public ApiSuccessResponse<Map<String, Integer>> updateProductStock(
//            @AuthOwner Long ownerId,
//            @PathVariable("bakery_id") Long bakeryId,
//            @PathVariable("product_id") Long productId,
//            @RequestBody @Valid ProductStockUpdateRequest request
//    ) {
//        Integer updatedStock = productService.updateProductStock(ownerId, bakeryId, productId, request.stock());
//        return ApiSuccessResponse.of("stock", updatedStock);
//    }
//}
