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

@Tag(name = "Customer - 고객 API", description = "고객 관련 정보의 등록, 조회 및 업데이트 API")
public interface CustomerControllerDocs {

	@Operation(
		summary = "고객 초기 정보 등록",
		description = "고객 ID와 초기 정보 요청 데이터를 기반으로 고객의 초기 정보를 등록합니다."
	)
	@DomainErrorCodeExamples({CUSTOMER_NOT_FOUND})
	ApiSuccessResponse<Void> initCustomerInfo(Long customerId, CustomerInitRequest request);

	@Operation(
		summary = "내 정보 조회",
		description = "고객 ID를 기반으로 고객 자신의 상세 정보를 조회합니다."
	)
	@DomainErrorCodeExamples({CUSTOMER_NOT_FOUND})
	ApiSuccessResponse<CustomerInfoResponse> getMyInfo(Long customerId);

	@Operation(
		summary = "닉네임 중복 확인",
		description = "입력한 닉네임의 중복 여부를 확인합니다."
	)
	ApiSuccessResponse<NicknameDuplicateResponse> checkDuplicateNickname(String nickname);

	@Operation(
		summary = "관심 지역 업데이트",
		description = "고객 ID와 관심 지역 업데이트 요청 데이터를 기반으로 고객의 관심 지역 정보를 갱신합니다."
	)
	@DomainErrorCodeExamples({CUSTOMER_NOT_FOUND})
	ApiSuccessResponse<Void> updateMyRegions(Long customerId, RegionUpdateRequest request);
}
