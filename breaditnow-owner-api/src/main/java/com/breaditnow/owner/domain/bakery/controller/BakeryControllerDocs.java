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

@Tag(name = "Owner - 빵집 API")
public interface BakeryControllerDocs {
	@Operation(summary = "소유자 정보를 바탕으로 빵집을 등록합니다.")
	@DomainErrorCodeExamples({OWNER_NOT_FOUND, DUPLICATE_OWNER_BAKERY, REGION_NOT_FOUND})
	@OwnerApiErrorCodeExamples({COORDINATE_NOT_FOUND})
	@ExternalErrorCodeExamples({FILE_CREATION_FAILED})
	ApiSuccessResponse<Map<String, Long>> createBakery(Long ownerId, BakeryCreateRequest request,
		MultipartFile profileImage);

	@Operation(summary = "빵집 아이디로 빵집의 상세 정보를 조회합니다.")
	@DomainErrorCodeExamples({BAKERY_NOT_FOUND, BAKERY_INACTIVE})
	ApiSuccessResponse<BakeryResponse> getBakery(Long bakeryId);

	@Operation(summary = "소유자 정보와 빵집 아이디를 이용해 빵집 정보를 수정합니다.")
	@DomainErrorCodeExamples({BAKERY_NOT_FOUND, BAKERY_INACTIVE, OWNER_MISMATCH, REGION_NOT_FOUND})
	@OwnerApiErrorCodeExamples({COORDINATE_NOT_FOUND})
	@ExternalErrorCodeExamples({FILE_CREATION_FAILED})
	ApiSuccessResponse<BakeryResponse> updateBakery(Long ownerId, Long bakeryId, BakeryUpdateRequest request,
		MultipartFile profileImage, List<MultipartFile> bakeryImages);

	@Operation(summary = "소유자 정보와 빵집 아이디를 이용해 빵집의 운영 상태를 업데이트합니다.")
	@DomainErrorCodeExamples({BAKERY_NOT_FOUND, BAKERY_INACTIVE, OWNER_MISMATCH})
	ApiSuccessResponse<Map<String, Long>> updateOperatingBakery(Long ownerId, Long bakeryId,
		OperatingStatusRequest request);

	@Operation(summary = "소유자 정보와 빵집 아이디를 이용해 빵집을 삭제합니다.")
	@DomainErrorCodeExamples({BAKERY_NOT_FOUND, BAKERY_INACTIVE, OWNER_MISMATCH})
	ApiSuccessResponse<Map<String, Long>> deleteBakery(Long ownerId, Long bakeryId);
}
