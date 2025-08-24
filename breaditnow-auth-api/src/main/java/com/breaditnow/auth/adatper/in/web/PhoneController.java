package com.breaditnow.auth.adatper.in.web;

import com.breaditnow.common.swagger.docs.PhoneControllerDocs;
import com.breaditnow.auth.application.dto.request.PhoneSendCodeRequest;
import com.breaditnow.auth.application.dto.request.PhoneVerifyCodeRequest;
import com.breaditnow.auth.application.PhoneService;
import com.breaditnow.common.response.ApiSuccessResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/phone")
@RequiredArgsConstructor
public class PhoneController implements PhoneControllerDocs {

    private final PhoneService phoneService;

    @PostMapping("/send-code")
    public ApiSuccessResponse<Void> sendCode(
            @RequestBody @Valid PhoneSendCodeRequest req) {
        phoneService.sendCode(req.phone());
        return ApiSuccessResponse.of();
    }

    @PostMapping("/verify-code")
    public ApiSuccessResponse<Map<String,Boolean>> verifyCode(
            @RequestBody @Valid PhoneVerifyCodeRequest req) {
        boolean ok = phoneService.verifyCode(req.phone(), req.code());
        return ApiSuccessResponse.of("verified", ok);
    }
}
