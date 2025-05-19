package com.breaditnow.customer.domain.customer.presentation;

import com.breaditnow.common.response.ApiSuccessResponse;
import com.breaditnow.customer.domain.customer.application.CustomerInitializationService;
import com.breaditnow.customer.domain.customer.application.CustomerProfileService;
import com.breaditnow.customer.domain.customer.application.CustomerRegionService;
import com.breaditnow.customer.domain.customer.application.request.CustomerInfoUpdateRequest;
import com.breaditnow.customer.domain.customer.application.request.CustomerInitRequest;
import com.breaditnow.customer.domain.customer.application.request.RegionUpdateRequest;
import com.breaditnow.customer.domain.customer.application.response.CustomerInfoResponse;
import com.breaditnow.customer.domain.customer.presentation.docs.CustomerControllerDocs;
import com.breaditnow.customer.global.security.annotation.AuthCustomer;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping("/api/v1/customer")
@RestController
@RequiredArgsConstructor
public class CustomerController  {
    private final CustomerInitializationService initializationService;
//    private final CustomerInfoQueryService queryService;
    private final CustomerProfileService profileService;
//    private final PasswordVerifyQueryService securityService;
//    private final NicknameDuplicateQueryService nicknameService;
    private final CustomerRegionService regionService;

    // 회원정보 초기 설정(첫 로그인)
    @PostMapping("/me/init")
    public ApiSuccessResponse<Void> initCustomerInfo(@AuthCustomer Long customerId, @RequestBody CustomerInitRequest dto) {
        initializationService.initCustomerInfo(customerId, dto);
        return ApiSuccessResponse.of();
    }

    /*// 내 정보 조회
    @GetMapping("/me/info")
    public ApiSuccessResponse<CustomerInfoResponse> getCustomerInfo(@AuthCustomer Long customerId) {
        return ApiSuccessResponse.of(queryService.getCustomerInfo(customerId));
    }
   */
    // 내 정보 수정
    @PatchMapping(value = "/me/info", consumes = "multipart/form-data")
    public ApiSuccessResponse<CustomerInfoResponse> updateCustomerInfo(
            @AuthCustomer Long customerId,
            @RequestPart("data") CustomerInfoUpdateRequest dto,
            @RequestPart(value = "profileImage", required = false) MultipartFile profileImage) {
        return ApiSuccessResponse.of(profileService.updateCustomerInfo(customerId, dto, profileImage));
    }

    /*
    // 현재 비밀번호 확인
    @PostMapping("/me/verify-password")
    public ApiSuccessResponse<PasswordVerifyResponse> verifyPassword(@AuthCustomer Long customerId, @RequestBody PasswordVerifyRequest dto) {
        return ApiSuccessResponse.of(securityService.verifyPassword(customerId, dto));
    }


    // 닉네임 중복 확인
    @GetMapping("/duplicate-nickname")
    public ApiSuccessResponse<NicknameDuplicateResponse> checkNicknameDuplicate(NicknameDuplicateCheckRequest dto) {
        return ApiSuccessResponse.of(nicknameService.isDuplicate(dto));
    }
    */

    // 관심 지역 설정
    @PatchMapping("/me/region")
    public ApiSuccessResponse<Void> updateRegion(@AuthCustomer Long customerId, @RequestBody RegionUpdateRequest dto) {
        regionService.updateRegion(customerId, dto);
        return ApiSuccessResponse.of();
    }
}
