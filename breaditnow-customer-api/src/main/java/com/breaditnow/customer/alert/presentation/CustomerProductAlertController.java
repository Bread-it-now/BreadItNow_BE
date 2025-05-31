package com.breaditnow.customer.alert.presentation;

import java.util.Map;

import com.breaditnow.customer.common.presentation.swagger.docs.CustomerProductAlertControllerDocs;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.breaditnow.common.response.ApiSuccessResponse;
import com.breaditnow.customer.alert.application.response.CustomerProductAlertPageResponse;
import com.breaditnow.customer.alert.application.CustomerProductAlertService;
import com.breaditnow.customer.common.security.annotation.AuthCustomer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/alert")
@Slf4j
public class CustomerProductAlertController implements CustomerProductAlertControllerDocs {

	private final CustomerProductAlertService customerProductAlertService;

	@PostMapping("/product/{productId}")
	public ApiSuccessResponse<Map<String, Long>> registerProductAlert(
		@AuthCustomer Long customerId,
		@PathVariable("productId") Long productId) {
		return customerProductAlertService.registerProductAlert(customerId, productId);
	}

	@DeleteMapping("/product/{productId}")
	public ApiSuccessResponse<Void> deleteProductAlert(
		@AuthCustomer Long customerId,
		@PathVariable("productId") Long productId) {
		customerProductAlertService.deactivateProductAlert(customerId, productId);
		return ApiSuccessResponse.of(null);
	}

	@PatchMapping("/product/{productId}/toggle")
	public ApiSuccessResponse<Map<String, Boolean>> toggleProductAlert(
		@AuthCustomer Long customerId,
		@PathVariable("productId") Long productId) {
		boolean newStatus = customerProductAlertService.toggleProductAlert(customerId, productId);
		return ApiSuccessResponse.of("active", newStatus);
	}

	@GetMapping("/product")
	public ApiSuccessResponse<CustomerProductAlertPageResponse> getProductAlerts(
		@AuthCustomer Long customerId,
		@RequestParam(name = "page", defaultValue = "0") int page,
		@RequestParam(name = "size", defaultValue = "10") int size
	) {
		return ApiSuccessResponse.of(customerProductAlertService.getProductAlerts(customerId, page, size));
	}
}
