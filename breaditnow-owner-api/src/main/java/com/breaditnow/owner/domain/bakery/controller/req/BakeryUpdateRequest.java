//package com.breaditnow.owner.domain.bakery.controller.req;
//
//import io.swagger.v3.oas.annotations.media.Schema;
//import jakarta.validation.constraints.NotBlank;
//import jakarta.validation.constraints.Pattern;
//
//public record BakeryUpdateRequest(
//        @NotBlank(message = "이름은 필수 입력 사항입니다.")
//        @Schema(description = "빵집 이름", example = "소금 두 꼬집")
//        String name,
//
//        @NotBlank(message = "법정행정동 코드는 필수 입력 사항입니다.")
//        @Schema(description = "수정된 빵집 법정 행정동 코드", example = "1168010500")
//        String addressCode,
//
//        @NotBlank(message = "주소는 필수 입력 사항입니다.")
//        @Schema(description = "수정된 빵집 상세 주소", example = "서울특별시 강남구 삼성동 100")
//        String address,
//
//        @NotBlank(message = "전화번호는 필수 입력 사항입니다.")
//        @Pattern(regexp = "\\d{2,3}-\\d{3,4}-\\d{4}", message = "전화번호 형식이 올바르지 않습니다. (예: 010-1234-5678)")
//        @Schema(description = "수정된 빵집 전화번호", example = "02-1234-5678")
//        String phone,
//
//        @Schema(description = "수정된 빵집 영업시간", example = "오전 07:30 ~ 오후 08:00")
//        String openTime,
//
//        @Schema(description = "수정된 빵집 소개글", example = "매일 더 신선해진 재료로, 더욱 맛있는 빵과 디저트를 준비합니다!")
//        String introduction
//) {
//}
