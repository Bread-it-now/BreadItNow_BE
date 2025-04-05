package com.breaditnow.customer.domain.region.controller;

import java.util.List;

import com.breaditnow.common.response.ApiSuccessResponse;
import com.breaditnow.customer.domain.region.controller.res.DongResponse;
import com.breaditnow.customer.domain.region.controller.res.GugunResponse;
import com.breaditnow.customer.domain.region.controller.res.SidoResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Customer - 지역 API")
public interface RegionControllerDocs {
	@Operation(summary = "전체 시도 목록을 조회합니다.")
	ApiSuccessResponse<List<SidoResponse>> getSidoList();

	@Operation(summary = "주어진 시도 코드를 기반으로 해당 시도의 구군 목록을 조회합니다.")
	ApiSuccessResponse<List<GugunResponse>> getGugunListBySido(String sidoCode);

	@Operation(summary = "주어진 구군 코드를 기반으로 해당 구군의 동 목록을 조회합니다.")
	ApiSuccessResponse<List<DongResponse>> getDongListByGugun(String gugunCode);
}
