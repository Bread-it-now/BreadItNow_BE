package com.breaditnow.common.swagger.docs;

import com.breaditnow.auth.application.dto.request.PhoneSendCodeRequest;
import com.breaditnow.auth.application.dto.request.PhoneVerifyCodeRequest;
import com.breaditnow.common.response.ApiSuccessResponse;
import io.swagger.v3.oas.annotations.Operation;

import java.util.Map;

public interface PhoneControllerDocs {

    @Operation(summary="휴대폰 인증 코드 발송")
    ApiSuccessResponse<Void> sendCode(PhoneSendCodeRequest req);

    @Operation(summary="휴대폰 인증 코드 검증")
    ApiSuccessResponse<Map<String,Boolean>> verifyCode(PhoneVerifyCodeRequest req);
}
