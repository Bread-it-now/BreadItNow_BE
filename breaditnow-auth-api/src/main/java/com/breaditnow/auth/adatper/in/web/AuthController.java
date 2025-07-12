package com.breaditnow.auth.adatper.in.web;

import com.breaditnow.auth.application.dto.request.DirectSignUpRequest;
import com.breaditnow.auth.domain.port.in.SignUpUseCase;
import com.breaditnow.common.response.ApiSuccessResponse;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.attribute.UserPrincipal;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final SignUpUseCase signUpUseCase;

    @PostMapping("/sign-up")
    public ApiSuccessResponse<Map<String, Long>> signUp(@RequestBody DirectSignUpRequest request) {
        Long userId = signUpUseCase.signUp(request);
        return ApiSuccessResponse.of("userId", userId);
    }

    @PostMapping("/logout")
    public ApiSuccessResponse<Void> logout(@AuthenticationPrincipal UserPrincipal principal, HttpServletResponse response) {

    }
}
