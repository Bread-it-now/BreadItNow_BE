package com.breaditnow.customer.customer.adapter.in.web;

import com.breaditnow.common.response.ApiSuccessResponse;
import com.breaditnow.customer.common.security.annotation.AuthCustomer;
import com.breaditnow.customer.customer.application.*;
import com.breaditnow.customer.customer.application.request.CustomerInfoUpdateRequest;
import com.breaditnow.customer.customer.application.request.CustomerInitRequest;
import com.breaditnow.customer.customer.application.request.NicknameDuplicateCheckRequest;
import com.breaditnow.customer.customer.application.request.RegionUpdateRequest;
import com.breaditnow.customer.customer.application.response.CustomerInfoResponse;
import com.breaditnow.customer.customer.application.response.NicknameDuplicateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RequestMapping("/api/v1/customer")
@RestController
@RequiredArgsConstructor
public class CustomerController  {
    private final CustomerInitializationService initializationService;
    private final CustomerProfileService profileService;
    private final CustomerRegionService regionService;
    private final NicknameDuplicateQueryService nicknameService;
    private final CustomerInfoQueryService queryService;

    // 회원정보 초기 설정 여부 확인(첫 로그인)
    @GetMapping("/me/init")
    public ApiSuccessResponse<Map<String, Boolean>> initCustomer(@AuthCustomer Long customerId) {
        Boolean firstLogin = initializationService.isFirstLogin(customerId);
        return ApiSuccessResponse.of(Map.of("isFirstLogin", firstLogin));
    }

    // 회원정보 초기 설정(첫 로그인)
    @PostMapping("/me/init")
    public ApiSuccessResponse<Void> initCustomerInfo(@AuthCustomer Long customerId, @RequestBody CustomerInitRequest dto) {
        initializationService.initCustomerInfo(customerId, dto);
        return ApiSuccessResponse.of();
    }

    // 내 정보 조회
    @GetMapping("/me/info")
    public ApiSuccessResponse<CustomerInfoResponse> getCustomerInfo(@AuthCustomer Long customerId) {
        return ApiSuccessResponse.of(queryService.getCustomerInfo(customerId));
    }

    // 내 정보 수정
    @PatchMapping(value = "/me/info", consumes = "multipart/form-data")
    public ApiSuccessResponse<CustomerInfoResponse> updateCustomerInfo(
            @AuthCustomer Long customerId,
            @RequestPart("data") CustomerInfoUpdateRequest dto,
            @RequestPart(value = "bakeryProfileImage", required = false) MultipartFile profileImage) {
        return ApiSuccessResponse.of(profileService.updateCustomerInfo(customerId, dto, profileImage));
    }

    // 닉네임 중복 확인
    @GetMapping("/duplicate-nickname")
    public ApiSuccessResponse<NicknameDuplicateResponse> checkNicknameDuplicate(NicknameDuplicateCheckRequest dto) {
        return ApiSuccessResponse.of(nicknameService.isDuplicate(dto));
    }

    // 관심 지역 설정
    @PatchMapping("/me/region")
    public ApiSuccessResponse<Void> updateRegion(@AuthCustomer Long customerId, @RequestBody RegionUpdateRequest dto) {
        regionService.updateRegion(customerId, dto);
        return ApiSuccessResponse.of();
    }
}
