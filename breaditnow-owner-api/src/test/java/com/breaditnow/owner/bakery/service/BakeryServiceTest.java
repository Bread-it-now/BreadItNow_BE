package com.breaditnow.owner.bakery.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.breaditnow.domain.owner.entity.Owner;
import com.breaditnow.domain.owner.repository.OwnerRepository;
import com.breaditnow.owner.bakery.controller.req.BakeryCreateRequest;
import com.breaditnow.owner.bakery.controller.res.BakeryResponse;

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
	void create_bakery() {
		// given
		Owner owner = new Owner("email", "password");
		ownerRepository.save(owner);

		bakeryCreateRequest = new BakeryCreateRequest(
			"맛있는 빵집",
			"1111010100",
			"서울시 종로구 청운동",
			"02-1234-5678",
			"09:00-21:00",
			"최고의 빵을 제공합니다.",
			"default.png"
		);

		Long bakeryId = bakeryService.createBakery(1L, bakeryCreateRequest);
		BakeryResponse bakery = bakeryService.getBakery(bakeryId);
		System.out.println(bakery);
	}
}
