package com.breaditnow.customer.alert.presentation;

import com.breaditnow.customer.alert.application.response.ProductAlertToggleResponse;
import org.springframework.web.bind.annotation.*;

import com.breaditnow.common.response.ApiSuccessResponse;
import com.breaditnow.customer.alert.application.ProductAlertService;
import com.breaditnow.customer.common.security.annotation.AuthCustomer;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/alert")
public class ProductAlertController {

	private final ProductAlertService productAlertService;

	@PostMapping("/product/{productId}")
	public ApiSuccessResponse<Void> registerProductAlert(@AuthCustomer Long customerId, @PathVariable("productId") Long productId) {
		productAlertService.registerProductAlert(customerId, productId);
		return ApiSuccessResponse.of();
	}

	@DeleteMapping("/product/{productId}")
	public ApiSuccessResponse<Void> deleteProductAlert(@AuthCustomer Long customerId, @PathVariable("productId") Long productId) {
		productAlertService.deleteProductAlert(customerId, productId);
		return ApiSuccessResponse.of();
	}

	@PatchMapping("/product/{productId}/toggle")
	public ApiSuccessResponse<ProductAlertToggleResponse> toggleProductAlert(
		@AuthCustomer Long customerId,
		@PathVariable("productId") Long productId) {
		return ApiSuccessResponse.of(productAlertService.toggleProductAlert(customerId, productId));
	}
//
//	@GetMapping("/product")
//	public ApiSuccessResponse<CustomerProductAlertPageResponse> getProductAlerts(
//		@AuthCustomer Long customerId,
//		@RequestParam(name = "page", defaultValue = "0") int page,
//		@RequestParam(name = "size", defaultValue = "10") int size
//	) {
//		return ApiSuccessResponse.of(productAlertService.getProductAlerts(customerId, page, size));
//	}
}
