package com.breaditnow.customer.domain.fcm.controller;

import com.breaditnow.common.response.ApiSuccessResponse;
import com.breaditnow.customer.domain.fcm.controller.req.FcmSendRequest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(
	name = "Customer - FCM Token API",
	description = "소유자의 FCM 토큰을 프론트엔드로부터 전달받아 저장·관리하기 위한 API입니다."
)
public interface FcmControllerDocs {

	@Operation(
		summary = "FCM 토큰 등록/갱신",
		description = "소유자 ID와 함께 FCM 토큰 정보를 전달받아 저장(또는 갱신)합니다. 이후 푸시 메시지 전송 시 이 토큰을 활용합니다."
	)
	ApiSuccessResponse<Void> pushMessage(Long customerId, FcmSendRequest fcmSendRequest);
}
