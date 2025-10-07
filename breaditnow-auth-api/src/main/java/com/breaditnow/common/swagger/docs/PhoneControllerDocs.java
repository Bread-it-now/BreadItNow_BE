package com.breaditnow.common.swagger.docs;

import com.breaditnow.auth.application.dto.request.PhoneSendCodeRequest;
import com.breaditnow.auth.application.dto.request.PhoneVerifyCodeRequest;
import com.breaditnow.common.response.ApiSuccessResponse;
import com.breaditnow.common.swagger.annotation.AuthApiErrorCodeExamples;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.Map;

import static com.breaditnow.common.exception.AuthErrorCode.CODE_EXPIRED;
import static com.breaditnow.common.exception.AuthErrorCode.CODE_MISMATCH;

@Tag(name = "Phone API", description = "휴대폰 인증 관련 API")
public interface PhoneControllerDocs {

    @Operation(summary = "인증번호 발송", description = "입력한 휴대폰 번호로 인증번호를 발송합니다.")
    @AuthApiErrorCodeExamples({CODE_EXPIRED})
    ApiSuccessResponse<Void> sendCode(PhoneSendCodeRequest req);

    @Operation(summary = "인증번호 검증", description = "입력한 인증번호가 올바른지 검증합니다.")
    @AuthApiErrorCodeExamples({CODE_EXPIRED, CODE_MISMATCH})
    ApiSuccessResponse<Map<String, Boolean>> verifyCode(PhoneVerifyCodeRequest req);
}