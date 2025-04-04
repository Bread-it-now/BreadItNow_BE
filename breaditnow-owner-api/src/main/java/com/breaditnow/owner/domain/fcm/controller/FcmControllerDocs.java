package com.breaditnow.owner.domain.fcm.controller;

import static com.breaditnow.domain.global.exception.DomainErrorCode.*;

import com.breaditnow.common.response.ApiSuccessResponse;
import com.breaditnow.owner.domain.fcm.controller.req.FcmSendRequest;
import com.breaditnow.owner.global.swagger.annotation.DomainErrorCodeExamples;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Owner - FCM API")
public interface FcmControllerDocs {
	@Operation(summary = "소유자 ID와 FCM 전송 요청 정보를 기반으로 FCM 메시지를 푸시합니다.")
	@DomainErrorCodeExamples({OWNER_NOT_FOUND})
	ApiSuccessResponse<Void> pushMessage(Long ownerId, FcmSendRequest fcmSendRequest);
}
