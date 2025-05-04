package com.breaditnow.customer.domain.customer.controller;

import static org.springframework.http.MediaType.*;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.breaditnow.common.response.ApiSuccessResponse;
import com.breaditnow.customer.domain.customer.controller.req.CustomerInfoUpdateRequest;
import com.breaditnow.customer.domain.customer.controller.req.CustomerInitRequest;
import com.breaditnow.customer.domain.customer.controller.req.PasswordVerifyRequest;
import com.breaditnow.customer.domain.customer.controller.req.RegionUpdateRequest;
import com.breaditnow.customer.domain.customer.controller.res.CustomerInfoResponse;
import com.breaditnow.customer.domain.customer.controller.res.NicknameDuplicateResponse;
import com.breaditnow.customer.domain.customer.controller.res.PasswordVerifyResponse;
import com.breaditnow.customer.domain.customer.service.CustomerService;
import com.breaditnow.customer.global.security.annotation.AuthCustomer;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/customer")
@Slf4j
public class CustomerController implements CustomerControllerDocs {

	private final CustomerService customerService;

	@PostMapping("/me/init")
	public ApiSuccessResponse<Void> initCustomerInfo(
		@AuthCustomer Long customerId,
		@RequestBody @Valid CustomerInitRequest request) {
		customerService.initCustomerInfo(customerId, request);
		return ApiSuccessResponse.of();
	}

	@PostMapping(value = "/me/info", consumes = {MULTIPART_FORM_DATA_VALUE})
	public ApiSuccessResponse<CustomerInfoResponse> updateCustomerInfo(
		@AuthCustomer Long customerId,
		@RequestPart("data") @Valid CustomerInfoUpdateRequest request,
		@RequestPart(value = "profileImage", required = false) MultipartFile profileImage) {
		return ApiSuccessResponse.of(customerService.updateCustomerInfo(customerId, request, profileImage));
	}

	@GetMapping("/me/info")
	public ApiSuccessResponse<CustomerInfoResponse> getCustomerInfo(
		@AuthCustomer Long customerId) {
		return ApiSuccessResponse.of(customerService.getCustomerInfo(customerId));
	}

	@PostMapping("/me/verify-password")
	public ApiSuccessResponse<PasswordVerifyResponse> verifyPassword(
		@AuthCustomer Long customerId,
		@RequestBody @Valid PasswordVerifyRequest request) {
		return ApiSuccessResponse.of(customerService.verifyPassword(customerId, request));
	}

	@GetMapping("/duplicate-nickname")
	public ApiSuccessResponse<NicknameDuplicateResponse> checkDuplicateNickname(
		@RequestParam("nickname") String nickname) {
		return ApiSuccessResponse.of(customerService.checkDuplicateNickname(nickname));
	}

	@PatchMapping("/me/region")
	public ApiSuccessResponse<Void> updateMyRegions(
		@AuthCustomer Long customerId,
		@RequestBody @Valid RegionUpdateRequest request) {
		customerService.updateCustomerRegions(customerId, request);
		return ApiSuccessResponse.of();
	}
}
