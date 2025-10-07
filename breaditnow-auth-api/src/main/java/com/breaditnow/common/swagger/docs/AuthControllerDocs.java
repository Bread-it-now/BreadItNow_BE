package com.breaditnow.common.swagger.docs;

import com.breaditnow.auth.adatper.in.security.AccountContext;
import com.breaditnow.auth.application.dto.request.DirectSignUpRequest;
import com.breaditnow.auth.application.dto.request.PasswordVerifyRequest;
import com.breaditnow.common.response.ApiSuccessResponse;
import com.breaditnow.common.swagger.annotation.AuthApiErrorCodeExamples;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import java.util.Map;

import static com.breaditnow.common.exception.AuthErrorCode.EMAIL_ALREADY_EXISTS;
import static com.breaditnow.common.exception.AuthErrorCode.USER_NOT_FOUND;

@Tag(name = "Auth API", description = "인증 관련 API")
public interface AuthControllerDocs {
    @Operation(summary = "직접 회원 가입", description = "회원 가입 요청을 처리하고, 신규 회원의 식별 정보를 반환합니다.")
    @AuthApiErrorCodeExamples({EMAIL_ALREADY_EXISTS, USER_NOT_FOUND})
    ApiSuccessResponse<Map<String, Long>> signUp(DirectSignUpRequest request);

    @Operation(summary = "로그아웃", description = "현재 사용자의 로그아웃을 처리합니다.")
    @AuthApiErrorCodeExamples({USER_NOT_FOUND})
    ApiSuccessResponse<Void> logout(@AuthenticationPrincipal AccountContext principal, HttpServletRequest request, HttpServletResponse response);

    @Operation(summary = "비밀번호 검증", description = "비밀번호가 올바른지 검증합니다.")
    @AuthApiErrorCodeExamples({USER_NOT_FOUND})
    ApiSuccessResponse<Map<String, Boolean>> verifyPassword(@AuthenticationPrincipal AccountContext principal, PasswordVerifyRequest request);
}
