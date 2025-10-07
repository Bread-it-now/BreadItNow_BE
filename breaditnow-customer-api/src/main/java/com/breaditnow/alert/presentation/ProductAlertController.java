package com.breaditnow.alert.presentation;

import com.breaditnow.common.response.ApiSuccessResponse;
import com.breaditnow.alert.application.ProductAlertService;
import com.breaditnow.alert.application.response.ProductAlertToggleResponse;
import com.breaditnow.alert.infrastructure.ProductAlertAdapter;
import com.breaditnow.alert.presentation.request.ProductAlertSearchRequest;
import com.breaditnow.alert.presentation.response.ProductAlertPageResponse;
import com.breaditnow.alert.presentation.response.TodayProductAlertListResponse;
import com.breaditnow.common.domain.Pagination;
import com.breaditnow.common.security.annotation.AuthCustomer;
import com.breaditnow.common.swagger.docs.ProductAlertControllerDocs;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/alert")
public class ProductAlertController implements ProductAlertControllerDocs {
	private final ProductAlertService productAlertService;
	private final ProductAlertAdapter productAlertAdapter;

	@PostMapping("/product/{productId}")
	public ApiSuccessResponse<Void> registerProductAlert(
			@AuthCustomer Long customerId,
			@PathVariable("productId") Long productId) {

		productAlertService.registerProductAlert(customerId, productId);
		return ApiSuccessResponse.of();
	}

	@DeleteMapping("/product/{productId}")
	public ApiSuccessResponse<Void> deleteProductAlert(
			@AuthCustomer Long customerId,
			@PathVariable("productId") Long productId) {

		productAlertService.deleteProductAlert(customerId, productId);
		return ApiSuccessResponse.of();
	}

	@PatchMapping("/product/{productId}/toggle")
	public ApiSuccessResponse<ProductAlertToggleResponse> toggleProductAlert(
			@AuthCustomer Long customerId,
			@PathVariable("productId") Long productId) {

		return ApiSuccessResponse.of(productAlertService.toggleProductAlert(customerId, productId));
	}

	@GetMapping("/today")
	public ApiSuccessResponse<TodayProductAlertListResponse> getTodayProductAlert(@AuthCustomer Long customerId) {
		return ApiSuccessResponse.of(productAlertAdapter.getTodayProductAlert(customerId));
	}

	@GetMapping("/product")
	public ApiSuccessResponse<ProductAlertPageResponse> getProductAlerts(
			@AuthCustomer Long customerId,
			ProductAlertSearchRequest request) {

		Pagination pagination = Pagination.of(request.page(), request.size());
		return ApiSuccessResponse.of(productAlertAdapter.getProductAlerts(customerId, pagination));
	}
}
