package com.breaditnow.common.swagger.docs;

import com.breaditnow.auth.application.dto.request.SendCodeRequest;
import com.breaditnow.auth.application.dto.request.VerifyCodeRequest;
import com.breaditnow.common.response.ApiSuccessResponse;
import com.breaditnow.common.swagger.annotation.AuthApiErrorCodeExamples;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.Map;

import static com.breaditnow.common.exception.AuthErrorCode.*;

@Tag(name = "Email Auth API", description = "이메일 중복 확인 및 인증 코드 관련 API")
public interface EmailControllerDocs {

    @Operation(summary = "이메일 중복 확인", description = "입력된 이메일이 이미 가입되어 있는지 확인합니다.")
    ApiSuccessResponse<Map<String, Boolean>> checkDuplicate(String email);

    @Operation(summary = "이메일 인증 코드 발송", description = "회원가입을 위해 이메일로 인증 코드를 발송합니다.")
    @AuthApiErrorCodeExamples({EMAIL_ALREADY_EXISTS})
    ApiSuccessResponse<Void> sendCode(SendCodeRequest request);

    @Operation(summary = "이메일 인증 코드 확인", description = "발송된 인증 코드가 유효한지 확인합니다.")
    @AuthApiErrorCodeExamples({CODE_EXPIRED, CODE_MISMATCH})
    ApiSuccessResponse<Map<String, Boolean>> verifyCode(VerifyCodeRequest request);
}
