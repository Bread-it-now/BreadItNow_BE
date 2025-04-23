package com.breaditnow.auth.domain.email.controller;

import com.breaditnow.auth.domain.email.service.EmailDuplicationService;
import com.breaditnow.common.response.ApiSuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/email")
public class EmailController implements EmailControllerDocs {

    private final EmailDuplicationService emailDuplicationService;

    @GetMapping("/duplicate-check")
    public ApiSuccessResponse<Map<String, Boolean>> checkDuplicate(String email) {
        boolean duplicated = emailDuplicationService.isDuplicated(email);
        return ApiSuccessResponse.of("duplicated", duplicated);
    }
}
