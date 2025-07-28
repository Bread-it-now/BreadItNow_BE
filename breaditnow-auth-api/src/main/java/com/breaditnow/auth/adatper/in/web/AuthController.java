package com.breaditnow.auth.adatper.in.web;

import com.breaditnow.auth.adatper.in.security.AccountContext;
import com.breaditnow.auth.application.dto.request.DirectSignUpRequest;
import com.breaditnow.auth.domain.port.in.LogoutUseCase;
import com.breaditnow.auth.domain.port.in.SignUpUseCase;
import com.breaditnow.common.response.ApiSuccessResponse;
import com.breaditnow.common.util.CookieUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final SignUpUseCase signUpUseCase;
    private final LogoutUseCase logoutUseCase;
    private final CookieUtil cookieUtil;

    @PostMapping("/sign-up")
    public ApiSuccessResponse<Map<String, Long>> signUp(@RequestBody DirectSignUpRequest request) {
        Long userId = signUpUseCase.signUp(request);
        return ApiSuccessResponse.of("userId", userId);
    }

    @PostMapping("/logout")
    public ApiSuccessResponse<Void> logout(@AuthenticationPrincipal AccountContext principal, HttpServletRequest request, HttpServletResponse response) {
        if (principal == null) {
            return ApiSuccessResponse.of();
        }

        Long userId = principal.getAccount().getId();
        logoutUseCase.logout(userId);

        cookieUtil.deleteCookie(request, response, "refreshToken");
        return ApiSuccessResponse.of();
    }
}
