package com.breaditnow.customer.domain.fcm.controller;

import com.breaditnow.common.response.ApiSuccessResponse;
import com.breaditnow.customer.domain.fcm.controller.req.FcmSendRequest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Customer - FCM API", description = "고객이 FCM 푸시 메시지를 전송하기 위한 API")
public interface FcmControllerDocs {

	@Operation(
		summary = "FCM 푸시 메시지 전송",
		description = "고객 ID와 FCM 전송 요청 데이터를 기반으로 FCM 푸시 메시지를 전송합니다. 요청 데이터에는 전송 대상 및 메시지 내용 등의 정보가 포함됩니다."
	)
	ApiSuccessResponse<Void> pushMessage(Long customerId, FcmSendRequest fcmSendRequest);
}
