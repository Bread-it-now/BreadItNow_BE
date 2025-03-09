package com.breaditnow.owner.domain.product.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.breaditnow.common.response.ApiSuccessResponse;
import com.breaditnow.owner.domain.product.controller.req.ProductCreateRequest;
import com.breaditnow.owner.domain.product.controller.req.ProductDeleteRequest;
import com.breaditnow.owner.domain.product.controller.req.ProductUpdateRequest;
import com.breaditnow.owner.domain.product.service.ProductService;
import com.breaditnow.owner.global.security.annotation.AuthOwner;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/bakery-product")
public class ProductController {

	private final ProductService productService;

	@PostMapping("/{bakeryId}")
	public ApiSuccessResponse<Map<String, Long>> createProduct(
		@AuthOwner Long ownerId,
		@PathVariable("bakeryId") Long bakeryId,
		@RequestPart("data") ProductCreateRequest request,
		@RequestPart(value = "productImage", required = false) MultipartFile productImage
	) {
		Long productId = productService.createProduct(ownerId, bakeryId, request, productImage);
		return ApiSuccessResponse.of("productId", productId);
	}

	@PutMapping("/{bakeryId}/product/{productId}")
	public ApiSuccessResponse<Map<String, Long>> updateProduct(
		@AuthOwner Long ownerId,
		@PathVariable("bakeryId") Long bakeryId,
		@PathVariable("productId") Long productId,
		@RequestPart("data") ProductUpdateRequest request,
		@RequestPart(value = "productImage", required = false) MultipartFile productImage
	) {
		productService.updateProduct(ownerId, bakeryId, productId, request, productImage);
		return ApiSuccessResponse.of();
	}

	@DeleteMapping("/{bakeryId}/product/{productId}")
	public ApiSuccessResponse<Map<String, Long>> deleteProduct(
		@AuthOwner Long ownerId,
		@PathVariable("bakeryId") Long bakeryId,
		@PathVariable("productId") Long productId
	) {
		Long deletedProductId = productService.deleteProduct(ownerId, bakeryId, productId);
		return ApiSuccessResponse.of("productId", deletedProductId);
	}

	@DeleteMapping("/{bakeryId}/products")
	public ApiSuccessResponse<Map<String, Integer>> deleteProducts(
		@AuthOwner Long ownerId,
		@PathVariable("bakeryId") Long bakeryId,
		@RequestBody ProductDeleteRequest productDeleteRequest
	) {
		int deletedCount = productService.deleteProducts(ownerId, bakeryId, productDeleteRequest.productIds());
		return ApiSuccessResponse.of("deletedCount", deletedCount);
	}
}
