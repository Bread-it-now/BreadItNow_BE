package com.breaditnow.customer.domain.fcm.controller;

import com.breaditnow.common.response.ApiSuccessResponse;
import com.breaditnow.customer.domain.fcm.controller.req.FcmSendRequest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Customer - FCM API")
public interface FcmControllerDocs {
	@Operation(summary = "고객 ID와 FCM 전송 요청 데이터를 기반으로 FCM 푸시 메시지를 전송합니다.")
	ApiSuccessResponse<Void> pushMessage(Long customerId, FcmSendRequest fcmSendRequest);
}
