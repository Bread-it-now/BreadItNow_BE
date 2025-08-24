package com.breaditnow.common.swagger.docs;

import com.breaditnow.auth.application.dto.request.SendCodeRequest;
import com.breaditnow.auth.application.dto.request.VerifyCodeRequest;
import com.breaditnow.common.response.ApiSuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Tag(name = "Auth API")
public interface EmailControllerDocs {

    @Operation(
            summary = "이메일 중복 여부 검사",
            description = "고객·사장님 계정 모두 포함해 이메일이 이미 사용 중인지 확인합니다."
    )
    ApiSuccessResponse<Map<String, Boolean>> checkDuplicate(
            @RequestParam("email")
            @NotBlank(message = "이메일은 필수 항목입니다.")
            String email
    );

    @Operation(
            summary = "이메일 인증 코드 발송",
            description = "중복되지 않은 이메일 주소로 6자리 인증 코드를 전송합니다."
    )
    ApiSuccessResponse<Map<String, Void>> sendCode(
            @Valid SendCodeRequest request);

    @Operation(summary = "이메일 인증 코드 검증")
    ApiSuccessResponse<Map<String, Boolean>> verifyCode(
            @Valid VerifyCodeRequest request);
}
