package com.breaditnow.customer.domain.customer.application.request;

import io.swagger.v3.oas.annotations.media.Schema;

public record CustomerInfoUpdateRequest(
        @Schema(description = "고객 이름", example = "홍길동", required = true)
        String nickname,

        @Schema(description = "고객 휴대폰 번호", example = "010-1234-5678", required = true)
        String phone,

        @Schema(description = "영문·숫자·특수문자를 포함한 8~30자리 비밀번호", example = "NewPassword123!")
        String newPassword
) {
}
