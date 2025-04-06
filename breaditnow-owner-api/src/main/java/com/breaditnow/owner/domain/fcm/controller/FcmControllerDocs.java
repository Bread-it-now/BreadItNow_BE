package com.breaditnow.owner.domain.fcm.controller;

import static com.breaditnow.domain.global.exception.DomainErrorCode.*;

import com.breaditnow.common.response.ApiSuccessResponse;
import com.breaditnow.owner.domain.fcm.controller.req.FcmSendRequest;
import com.breaditnow.owner.global.swagger.annotation.DomainErrorCodeExamples;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Owner - FCM API", description = "소유자에게 FCM 푸시 메시지를 전송하는 API입니다.")
public interface FcmControllerDocs {

	@Operation(
		summary = "FCM 메시지 푸시",
		description = "소유자 ID와 FCM 전송 요청 데이터를 기반으로, 대상 소유자에게 FCM 푸시 메시지를 전송합니다. 요청 데이터에는 FCM 토큰, 메시지 제목, 본문 등의 정보가 포함되어 있으며, 이를 통해 메시지 전송 결과를 확인할 수 있습니다."
	)
	@DomainErrorCodeExamples({OWNER_NOT_FOUND})
	ApiSuccessResponse<Void> pushMessage(Long ownerId, FcmSendRequest fcmSendRequest);
}
