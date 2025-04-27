package com.breaditnow.auth.domain.email.controller;

import com.breaditnow.auth.domain.email.controller.req.SendCodeRequest;
import com.breaditnow.auth.domain.email.controller.req.VerifyCodeRequest;
import com.breaditnow.auth.domain.email.service.EmailAuthService;
import com.breaditnow.auth.domain.email.service.EmailDuplicationService;
import com.breaditnow.common.response.ApiSuccessResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/email")
public class EmailController implements EmailControllerDocs {

    private final EmailDuplicationService emailDuplicationService;
    private final EmailAuthService emailAuthService;

    @GetMapping("/duplicate-check")
    public ApiSuccessResponse<Map<String, Boolean>> checkDuplicate(
            @RequestParam("email")
            @NotBlank(message = "이메일은 필수 항목입니다.")
            String email) {
        boolean duplicated = emailDuplicationService.isDuplicated(email);
        return ApiSuccessResponse.of("duplicated", duplicated);
    }

    @PostMapping("/send-code")
    public ApiSuccessResponse<Map<String, Void>> sendCode(
            @RequestBody @Valid SendCodeRequest request) {

        emailAuthService.sendAuthCode(request.email(), request.role());
        return ApiSuccessResponse.of();
    }

    @PostMapping("/verify-code")
    public ApiSuccessResponse<Map<String, Boolean>> verifyCode(
            @RequestBody @Valid VerifyCodeRequest req) {

        emailAuthService.verifyCode(req.email(), req.code());
        return ApiSuccessResponse.of("verified", true);
    }
}
