package com.breaditnow.owner.bakery.service;

import static org.assertj.core.api.Assertions.*;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.breaditnow.domain.domain.owner.entity.Owner;
import com.breaditnow.domain.domain.owner.repository.OwnerRepository;
import com.breaditnow.owner.domain.bakery.controller.req.BakeryCreateRequest;
import com.breaditnow.owner.domain.bakery.controller.req.BakeryUpdateRequest;
import com.breaditnow.owner.domain.bakery.controller.res.BakeryResponse;
import com.breaditnow.owner.domain.bakery.service.BakeryService;

@SpringBootTest
@Transactional
class BakeryServiceTest {
	@Autowired
	private BakeryService bakeryService;
	@Autowired
	private OwnerRepository ownerRepository;
	private BakeryCreateRequest bakeryCreateRequest;

	@Test
	@DisplayName("빵집을 생성하고 조회한다")
	@Rollback(value = false)
	void createBakery() {
		// given
		Owner owner = Owner.builder()
			.email("email")
			.password("password")
			.build();
		ownerRepository.save(owner);

		bakeryCreateRequest = new BakeryCreateRequest(
			"맛있는 빵집",
			"1111010100",
			"서울시 종로구 청운동",
			"02-1234-5678",
			"09:00-21:00",
			"최고의 빵을 제공합니다."
		);
		MockMultipartFile mockMultipartFile = new MockMultipartFile("mockFile", "파일 내용".getBytes());

		Long bakeryId = bakeryService.createBakery(1L, bakeryCreateRequest, mockMultipartFile);
		BakeryResponse bakeryResponse = bakeryService.getBakery(bakeryId);

		assertThat(bakeryResponse.bakeryId()).isNotNull();
		assertThat(bakeryResponse)
			.extracting("name", "phone", "addressDescription", "openTime", "introduction")
			.contains("맛있는 빵집", "02-1234-5678", "서울시 종로구 청운동", "09:00-21:00", "최고의 빵을 제공합니다.");
	}

	@Test
	@DisplayName("빵집 정보를 수정한다")
	@Rollback(value = false)
	void updateBakery() {
		// given
		Owner owner = Owner.builder()
			.email("email")
			.password("password")
			.build();
		Owner savedOwner = ownerRepository.save(owner);

		bakeryCreateRequest = new BakeryCreateRequest(
			"맛있는 빵집",
			"1111010100",
			"서울시 종로구 청운동",
			"02-1234-5678",
			"09:00-21:00",
			"최고의 빵을 제공합니다."
		);
		MockMultipartFile mockMultipartFile = new MockMultipartFile(
			"file",
			"integration-test.txt",
			"text/plain",
			"text 파일".getBytes(StandardCharsets.UTF_8)
		);

		Long bakeryId = bakeryService.createBakery(savedOwner.getId(), bakeryCreateRequest, mockMultipartFile);

		MockMultipartFile updatedMockMultipartFile = new MockMultipartFile(
			"updated-file",
			"updated-integration-test.txt",
			"text/plain",
			"updated text 파일".getBytes(StandardCharsets.UTF_8)
		);

		BakeryUpdateRequest bakeryUpdateRequest = new BakeryUpdateRequest(
			"맛있는 빵집1",
			"1111010100",
			"서울시 종로구 청운동11",
			"02-5678-5678",
			"10:00-21:00",
			"최고의 빵을 제공합니다!!!"
		);
		MockMultipartFile bakeryImage1 = new MockMultipartFile(
			"file1",
			"integration-test.txt",
			"text/plain",
			"text 파일".getBytes(StandardCharsets.UTF_8)
		);

		MockMultipartFile bakeryImage2 = new MockMultipartFile(
			"file2",
			"integration-test.txt",
			"text/plain",
			"text 파일".getBytes(StandardCharsets.UTF_8)
		);
		List<MultipartFile> mockMultipartFiles = new ArrayList<>();
		mockMultipartFiles.add(bakeryImage1);
		mockMultipartFiles.add(bakeryImage2);

		// when
		BakeryResponse bakeryResponse = bakeryService.updateBakery(owner.getId(), bakeryId, bakeryUpdateRequest,
			updatedMockMultipartFile, mockMultipartFiles);

		// then
		assertThat(bakeryResponse.bakeryId()).isEqualTo(bakeryId);
		assertThat(bakeryResponse)
			.extracting("name", "phone", "addressDescription", "openTime", "introduction")
			.contains("맛있는 빵집1", "02-5678-5678", "서울시 종로구 청운동11", "10:00-21:00", "최고의 빵을 제공합니다!!!");
	}
}
