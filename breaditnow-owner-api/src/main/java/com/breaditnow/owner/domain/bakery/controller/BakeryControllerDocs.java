package com.breaditnow.owner.domain.bakery.controller;

import static com.breaditnow.domain.global.exception.DomainErrorCode.*;
import static com.breaditnow.external.global.exception.ExternalErrorCode.*;
import static com.breaditnow.owner.global.exception.OwnerErrorCode.*;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.breaditnow.common.response.ApiSuccessResponse;
import com.breaditnow.owner.domain.bakery.controller.req.BakeryCreateRequest;
import com.breaditnow.owner.domain.bakery.controller.req.BakeryUpdateRequest;
import com.breaditnow.owner.domain.bakery.controller.req.OperatingStatusRequest;
import com.breaditnow.owner.domain.bakery.controller.res.BakeryResponse;
import com.breaditnow.owner.global.swagger.annotation.DomainErrorCodeExamples;
import com.breaditnow.owner.global.swagger.annotation.ExternalErrorCodeExamples;
import com.breaditnow.owner.global.swagger.annotation.OwnerApiErrorCodeExamples;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Owner - 빵집 API", description = "소유자를 위한 빵집 관리 API입니다. 이 API는 빵집 생성, 조회, 수정, 운영 상태 업데이트 및 삭제 기능을 제공합니다.")
public interface BakeryControllerDocs {

	@Operation(
		summary = "빵집 등록",
		description = "소유자 정보를 바탕으로 신규 빵집을 등록합니다. 요청 데이터에는 빵집의 기본 정보와 프로필 이미지가 포함됩니다."
	)
	@DomainErrorCodeExamples({OWNER_NOT_FOUND, DUPLICATE_OWNER_BAKERY, REGION_NOT_FOUND})
	@OwnerApiErrorCodeExamples({COORDINATE_NOT_FOUND})
	@ExternalErrorCodeExamples({FILE_CREATION_FAILED})
	ApiSuccessResponse<Map<String, Long>> createBakery(Long ownerId, BakeryCreateRequest request,
		MultipartFile profileImage);

	@Operation(
		summary = "빵집 상세 조회",
		description = "빵집 ID를 기반으로 해당 빵집의 상세 정보를 조회합니다."
	)
	@DomainErrorCodeExamples({BAKERY_NOT_FOUND, BAKERY_INACTIVE})
	ApiSuccessResponse<BakeryResponse> getBakery(Long bakeryId);

	@Operation(
		summary = "빵집 정보 수정",
		description = "소유자 정보와 빵집 ID를 이용해 빵집의 정보를 수정합니다. 요청 데이터에는 빵집의 업데이트된 정보, 프로필 이미지 및 추가 이미지 목록이 포함됩니다."
	)
	@DomainErrorCodeExamples({BAKERY_NOT_FOUND, BAKERY_INACTIVE, OWNER_MISMATCH, REGION_NOT_FOUND})
	@OwnerApiErrorCodeExamples({COORDINATE_NOT_FOUND})
	@ExternalErrorCodeExamples({FILE_CREATION_FAILED})
	ApiSuccessResponse<BakeryResponse> updateBakery(Long ownerId, Long bakeryId, BakeryUpdateRequest request,
		MultipartFile profileImage, List<MultipartFile> bakeryImages);

	@Operation(
		summary = "운영 상태 업데이트",
		description = "소유자 정보와 빵집 ID를 기반으로 빵집의 운영 상태를 변경합니다. 요청 데이터에는 새로운 운영 상태 정보가 포함됩니다."
	)
	@DomainErrorCodeExamples({BAKERY_NOT_FOUND, BAKERY_INACTIVE, OWNER_MISMATCH})
	ApiSuccessResponse<Map<String, Long>> updateOperatingBakery(Long ownerId, Long bakeryId,
		OperatingStatusRequest request);

	@Operation(
		summary = "빵집 삭제",
		description = "소유자 정보와 빵집 ID를 기반으로 해당 빵집을 삭제합니다. 삭제 시 빵집과 관련된 모든 정보가 제거됩니다."
	)
	@DomainErrorCodeExamples({BAKERY_NOT_FOUND, BAKERY_INACTIVE, OWNER_MISMATCH})
	ApiSuccessResponse<Map<String, Long>> deleteBakery(Long ownerId, Long bakeryId);
}
