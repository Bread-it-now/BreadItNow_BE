package com.breaditnow.owner.domain.owner.controller;

import static com.breaditnow.domain.global.exception.DomainErrorCode.*;

import com.breaditnow.common.response.ApiSuccessResponse;
import com.breaditnow.owner.domain.owner.controller.req.OwnerPasswordUpdateRequest;
import com.breaditnow.owner.common.presentation.annotation.DomainErrorCodeExamples;
import com.breaditnow.owner.common.presentation.annotation.OwnerApiErrorCodeExamples;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Owner - 내 정보 API", description = "비밀번호 수정 등을 위한 API입니다.")
public interface OwnerControllerDocs {

	@Operation(summary = "비밀번호 수정", description = "새로운 비밀번호로 갱신합니다.")
	@DomainErrorCodeExamples({OWNER_NOT_FOUND})
	@OwnerApiErrorCodeExamples({PASSWORD_SAME_AS_CURRENT})
	ApiSuccessResponse<Void> updateOwnerPassword(Long ownerId, OwnerPasswordUpdateRequest request);
}
