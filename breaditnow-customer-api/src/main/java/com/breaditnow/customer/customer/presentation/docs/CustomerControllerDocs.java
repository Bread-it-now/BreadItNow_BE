package com.breaditnow.customer.customer.presentation.docs;

import com.breaditnow.common.response.ApiSuccessResponse;
import com.breaditnow.customer.common.swagger.annotation.DomainErrorCodeExamples;
import com.breaditnow.customer.customer.application.request.*;
import com.breaditnow.customer.customer.application.response.CustomerInfoResponse;
import com.breaditnow.customer.customer.application.response.NicknameDuplicateResponse;
import com.breaditnow.customer.customer.application.response.PasswordVerifyResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.multipart.MultipartFile;

import static com.breaditnow.domain.global.exception.DomainErrorCode.CUSTOMER_NOT_FOUND;
import static io.swagger.v3.oas.annotations.enums.ParameterIn.QUERY;

@Tag(name = "Customer - 내 정보 API", description = "프로필 초기 설정, 정보 조회, 닉네임 중복 확인, 지역 정보 업데이트 등을 위한 API입니다.")
public interface CustomerControllerDocs {

    @Operation(summary = "프로필 초기 설정", description = "회원가입 이후, 추가 정보를 등록합니다.")
    @DomainErrorCodeExamples({CUSTOMER_NOT_FOUND})
    ApiSuccessResponse<Void> initCustomerInfo(Long customerId, CustomerInitRequest dto);

    @Operation(summary = "내 정보 수정", description = "회원 정보를 수정합니다.")
    @DomainErrorCodeExamples({CUSTOMER_NOT_FOUND})
    ApiSuccessResponse<CustomerInfoResponse> updateCustomerInfo(Long customerId, CustomerInfoUpdateRequest dto,
                                                                MultipartFile profileImage);

    @Operation(summary = "내 정보 조회", description = "자신의 상세 정보를 조회합니다.")
    @DomainErrorCodeExamples({CUSTOMER_NOT_FOUND})
    ApiSuccessResponse<CustomerInfoResponse> getCustomerInfo(Long customerId);

    @Operation(summary = "현재 비밀번호 확인", description = "입력한 비밀번호가 현재 비밀번호와 일치하는지 확인합니다.")
    @DomainErrorCodeExamples({CUSTOMER_NOT_FOUND})
    ApiSuccessResponse<PasswordVerifyResponse> verifyPassword(Long customerId, PasswordVerifyRequest dto);

    @Operation(summary = "닉네임 중복 확인", description = "입력한 닉네임의 중복 여부를 확인합니다.")
    @Parameter(name = "nickname", description = "중복 확인할 닉네임", example = "홍길동", in = QUERY, required = true)
    ApiSuccessResponse<NicknameDuplicateResponse> checkNicknameDuplicate(NicknameDuplicateCheckRequest dto);

    @Operation(summary = "관심 지역 업데이트", description = "관심 지역 정보를 갱신합니다.")
    @DomainErrorCodeExamples({CUSTOMER_NOT_FOUND})
    ApiSuccessResponse<Void> updateRegion(Long customerId, RegionUpdateRequest dto);
}
