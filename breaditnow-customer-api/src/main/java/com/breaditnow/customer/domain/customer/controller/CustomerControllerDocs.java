package com.breaditnow.customer.domain.customer.controller;

import static com.breaditnow.domain.global.exception.DomainErrorCode.*;
import static io.swagger.v3.oas.annotations.enums.ParameterIn.*;

import com.breaditnow.common.response.ApiSuccessResponse;
import com.breaditnow.customer.domain.customer.controller.req.CustomerInitRequest;
import com.breaditnow.customer.domain.customer.controller.req.PasswordVerifyRequest;
import com.breaditnow.customer.domain.customer.controller.req.RegionUpdateRequest;
import com.breaditnow.customer.domain.customer.controller.res.CustomerInfoResponse;
import com.breaditnow.customer.domain.customer.controller.res.NicknameDuplicateResponse;
import com.breaditnow.customer.domain.customer.controller.res.PasswordVerifyResponse;
import com.breaditnow.customer.global.swagger.annotation.DomainErrorCodeExamples;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Customer - 내 정보 API", description = "프로필 초기 설정, 정보 조회, 닉네임 중복 확인, 지역 정보 업데이트 등을 위한 API입니다.")
public interface CustomerControllerDocs {

	@Operation(summary = "프로필 초기 설정", description = "회원가입 이후, 추가 정보를 등록합니다.")
	@DomainErrorCodeExamples({CUSTOMER_NOT_FOUND})
	ApiSuccessResponse<Void> initCustomerInfo(Long customerId, CustomerInitRequest request);

	@Operation(summary = "내 정보 조회", description = "자신의 상세 정보를 조회합니다.")
	@DomainErrorCodeExamples({CUSTOMER_NOT_FOUND})
	ApiSuccessResponse<CustomerInfoResponse> getMyInfo(Long customerId);

	@Operation(summary = "현재 비밀번호 확인", description = "입력한 비밀번호가 현재 비밀번호와 일치하는지 확인합니다.")
	@DomainErrorCodeExamples({CUSTOMER_NOT_FOUND})
	ApiSuccessResponse<PasswordVerifyResponse> verifyPassword(Long customerId, PasswordVerifyRequest request);

	@Operation(summary = "닉네임 중복 확인", description = "입력한 닉네임의 중복 여부를 확인합니다.")
	@Parameter(name = "nickname", description = "중복 확인할 닉네임", example = "홍길동", in = QUERY, required = true)
	ApiSuccessResponse<NicknameDuplicateResponse> checkDuplicateNickname(String nickname);

	@Operation(summary = "관심 지역 업데이트", description = "관심 지역 정보를 갱신합니다.")
	@DomainErrorCodeExamples({CUSTOMER_NOT_FOUND})
	ApiSuccessResponse<Void> updateMyRegions(Long customerId, RegionUpdateRequest request);
}
