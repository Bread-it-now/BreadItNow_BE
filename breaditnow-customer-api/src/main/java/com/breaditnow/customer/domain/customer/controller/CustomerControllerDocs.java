package com.breaditnow.customer.domain.customer.controller;

import static com.breaditnow.domain.global.exception.DomainErrorCode.*;

import com.breaditnow.common.response.ApiSuccessResponse;
import com.breaditnow.customer.domain.customer.controller.req.CustomerInitRequest;
import com.breaditnow.customer.domain.customer.controller.req.RegionUpdateRequest;
import com.breaditnow.customer.domain.customer.controller.res.CustomerInfoResponse;
import com.breaditnow.customer.domain.customer.controller.res.NicknameDuplicateResponse;
import com.breaditnow.customer.global.swagger.annotation.DomainErrorCodeExamples;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Customer - 고객 API")
public interface CustomerControllerDocs {
	@Operation(summary = "고객 초기 정보를 등록합니다.")
	@DomainErrorCodeExamples({CUSTOMER_NOT_FOUND})
	ApiSuccessResponse<Void> initCustomerInfo(Long customerId, CustomerInitRequest request);

	@Operation(summary = "고객 자신의 정보를 조회합니다.")
	@DomainErrorCodeExamples({CUSTOMER_NOT_FOUND})
	ApiSuccessResponse<CustomerInfoResponse> getMyInfo(Long customerId);

	@Operation(summary = "닉네임 중복 여부를 확인합니다.")
	ApiSuccessResponse<NicknameDuplicateResponse> checkDuplicateNickname(String nickname);

	@Operation(summary = "고객의 관심 지역 정보를 업데이트합니다.")
	@DomainErrorCodeExamples({CUSTOMER_NOT_FOUND})
	ApiSuccessResponse<Void> updateMyRegions(Long customerId, RegionUpdateRequest request);
}
