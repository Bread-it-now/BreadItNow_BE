/*
package com.breaditnow.customer.product.controller;

import com.breaditnow.customer.common.presentation.swagger.docs.ProductControllerDocs;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.breaditnow.common.response.ApiSuccessResponse;
import com.breaditnow.customer.product.controller.res.HotProductPageResponse;
import com.breaditnow.customer.product.service.ProductPageService;
import com.breaditnow.customer.common.security.annotation.AuthCustomer;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/product")
public class ProductController implements ProductControllerDocs {
	private final ProductPageService productPageService;

	@GetMapping("/hot")
	public ApiSuccessResponse<HotProductPageResponse> searchHotProducts(@AuthCustomer(required = false) Long customerId,
		@RequestParam(name = "page", defaultValue = "0") int page,
		@RequestParam(name = "size", defaultValue = "10") int size,
		@RequestParam(name = "sort", defaultValue = "reservation") String sort) {
		return ApiSuccessResponse.of(productPageService.searchHotProducts(customerId, page, size, sort));
	}
}
*/
