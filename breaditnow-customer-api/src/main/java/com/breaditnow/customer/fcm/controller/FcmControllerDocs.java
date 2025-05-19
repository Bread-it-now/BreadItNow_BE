package com.breaditnow.customer.fcm.controller;

import static com.breaditnow.domain.global.exception.DomainErrorCode.*;

import com.breaditnow.common.response.ApiSuccessResponse;
import com.breaditnow.customer.fcm.controller.req.FcmSendRequest;
import com.breaditnow.customer.common.swagger.annotation.DomainErrorCodeExamples;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(
	name = "Customer - FCM Token API",
	description = "FCM 토큰을 프론트엔드로부터 전달받아 저장·관리하기 위한 API입니다."
)
public interface FcmControllerDocs {

	@Operation(summary = "FCM 토큰 등록/갱신", description = "FCM 토큰 정보를 전달받아 저장(또는 갱신)합니다. 이후 푸시 메시지 전송 시 이 토큰을 활용합니다.")
	@DomainErrorCodeExamples({CUSTOMER_NOT_FOUND})
	ApiSuccessResponse<Void> pushMessage(Long customerId, FcmSendRequest fcmSendRequest);
}
