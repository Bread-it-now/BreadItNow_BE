package com.breaditnow.owner.domain.bakery.controller;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.breaditnow.common.response.ApiSuccessResponse;
import com.breaditnow.domain.global.exception.DomainErrorCode;
import com.breaditnow.external.domain.swagger.ApiErrorCodeExample;
import com.breaditnow.owner.domain.bakery.controller.req.BakeryCreateRequest;
import com.breaditnow.owner.domain.bakery.controller.req.BakeryUpdateRequest;
import com.breaditnow.owner.domain.bakery.controller.req.OperatingStatusRequest;
import com.breaditnow.owner.domain.bakery.controller.res.BakeryResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Owner - 빵집 API")
public interface BakeryControllerDocs {
	@Operation(summary = "소유자 정보를 바탕으로 빵집을 등록합니다.")
	@ApiErrorCodeExample(DomainErrorCode.class)
	ApiSuccessResponse<Map<String, Long>> createBakery(Long ownerId, BakeryCreateRequest request,
		MultipartFile profileImage);

	@Operation(summary = "빵집 아이디로 빵집의 상세 정보를 조회합니다.")
	@ApiErrorCodeExample(DomainErrorCode.class)
	ApiSuccessResponse<BakeryResponse> getBakery(Long bakeryId);

	@Operation(
		summary = "빵집 수정",
		description = "소유자 정보와 빵집 아이디를 이용해 빵집 정보를 수정합니다."
	)
	ApiSuccessResponse<BakeryResponse> updateBakery(Long ownerId, Long bakeryId, BakeryUpdateRequest request,
		MultipartFile profileImage, List<MultipartFile> bakeryImages);

	@Operation(
		summary = "빵집 운영 상태 변경",
		description = "소유자 정보와 빵집 아이디를 이용해 빵집의 운영 상태를 업데이트합니다."
	)
	ApiSuccessResponse<Map<String, Long>> updateOperatingBakery(Long ownerId, Long bakeryId,
		OperatingStatusRequest request);

	@Operation(
		summary = "빵집 삭제",
		description = "소유자 정보와 빵집 아이디를 이용해 빵집을 삭제합니다."
	)
	ApiSuccessResponse<Map<String, Long>> deleteBakery(Long ownerId, Long bakeryId);
}
