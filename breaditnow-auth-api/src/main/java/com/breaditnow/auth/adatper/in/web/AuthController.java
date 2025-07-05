package com.breaditnow.auth.adatper.in.web;

import com.breaditnow.auth.application.dto.request.DirectSignUpRequest;
import com.breaditnow.auth.domain.port.in.SignUpUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final SignUpUseCase signUpUseCase;

    @PostMapping("/sign-up")
    public ResponseEntity<Void> signUp(@RequestBody DirectSignUpRequest request) {
        signUpUseCase.signUp(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
